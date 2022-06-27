package com.project.orderProcessing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.orderProcessing.model.OrderDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    UpdateOrderProcessingService updateOrderProcessingService;

    @Autowired
    ObjectMapper mapper;

    Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    @KafkaListener(topics = "order-processing-topic",groupId = "group-id")
    void consumeMessage(@Payload String jsonData){
        log.info("Consumed message -->"+jsonData);
        OrderDetails orderDetails = new OrderDetails();
        try {
            orderDetails = mapper.readValue(jsonData, new TypeReference<OrderDetails>(){});
        } catch (JsonProcessingException e) {
            log.error("Failed to consume message from kafka"+e);
            throw new RuntimeException(e);
        }
        log.info("Order processed successfully"+orderDetails.toString());
        updateOrderProcessingService.updateOrderProcessingStatus(orderDetails.getOrderId());
    }
}
