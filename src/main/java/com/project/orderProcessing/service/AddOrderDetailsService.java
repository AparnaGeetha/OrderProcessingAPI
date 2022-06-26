package com.project.orderProcessing.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.orderProcessing.model.MongoDetails;
import com.project.orderProcessing.model.OrderDetails;
import com.project.orderProcessing.repository.OrderProcessingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Map;

@Service
public class AddOrderDetailsService {

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    OrderProcessingRepository orderProcessingRepository;

    @Autowired
    KafkaProducerService kafkaProducerService;

    @Autowired
    ObjectMapper objectMapper;

    public void addOrderProcessingDetails(OrderDetails orderDetails){
        System.out.println("Start: Adding data to mongo");
        MongoDetails mongoDetails = new MongoDetails();
        mongoDetails.setOrderId(orderDetails.getOrderId());
        mongoDetails.setOrderQuantity(orderDetails.getOrderQuantity());
        mongoDetails.setProductName(orderDetails.getProductName());
        mongoDetails.setOrderStatus(orderDetails.getOrderStatus());
        orderProcessingRepository.save(mongoDetails);
        System.out.println("End: Added data to mongo");

        System.out.println("Start: Adding data to kafka");
        String message = null;
        try {
            message = objectMapper.writeValueAsString(orderDetails);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        kafkaProducerService.sendMessage(message);
        System.out.println("Added data to kafka -->"+message);

    }

}
