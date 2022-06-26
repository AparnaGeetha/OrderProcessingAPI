package com.project.orderProcessing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.orderProcessing.model.OrderDetails;
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
    ProcessService processService;

    @Autowired
    ObjectMapper mapper;

    @KafkaListener(topics = "order-processing-topic")
    void consumeMessage(@Payload String jsonData){
        System.out.println(jsonData);
        OrderDetails orderDetails = new OrderDetails();
        try {
            orderDetails = mapper.readValue(jsonData, new TypeReference<OrderDetails>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Order processed successfully");
        System.out.println(orderDetails);
        updateOrderProcessingService.updateOrderProcessingStatus(orderDetails.getOrderId());
//        processService.orderProcessing(orderDetails);
    }
}
