package com.project.orderProcessing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.orderProcessing.model.MongoDetails;
import com.project.orderProcessing.model.OrderDetails;
import com.project.orderProcessing.repository.OrderProcessingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.log4j2.Log4J2LoggingSystem;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.stereotype.Service;

@Service
public class AddOrderDetailsService {

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    OrderProcessingRepository orderProcessingRepository;

    @Autowired
    KafkaProducerService kafkaProducerService;

    Logger log = LoggerFactory.getLogger(AddOrderDetailsService.class);

    @Autowired
    ObjectMapper objectMapper;

    public void addOrderProcessingDetails(OrderDetails orderDetails){
        log.info("Start: Adding data to mongodb");
        MongoDetails mongoDetails = new MongoDetails();
        mongoDetails.setOrderId(orderDetails.getOrderId());
        mongoDetails.setOrderQuantity(orderDetails.getOrderQuantity());
        mongoDetails.setProductName(orderDetails.getProductName());
        mongoDetails.setOrderStatus(orderDetails.getOrderStatus());
        try {
            orderProcessingRepository.save(mongoDetails);
            log.info("End: Added data to mongodb");
        }catch (DataAccessResourceFailureException e) {
            log.error("Failed to save data to mongodb --> "+e);
        }
        log.info("Start: Adding data to kafka");
        String message = null;
        try {
            message = objectMapper.writeValueAsString(orderDetails);
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try{
            kafkaProducerService.sendMessage(message);
            log.info("Added data to kafka -->"+message);
        }catch (KafkaProducerException exception) {
            log.error("Failed to produce data to kafka -->"+exception);
            throw new RuntimeException(exception);
        }
    }

}
