package com.project.orderProcessing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumerService {

    @Autowired
    UpdateOrderProcessingService updateOrderProcessingService;

    @KafkaListener(topics = "order_processing_topic", groupId = "group_id")
    void consumeMessage(String message){
        System.out.println(message);
    }
    void updateOrderProcessingStatusInMongo(){
        updateOrderProcessingService.updateOrderProcessingStatus("2");
    }

}
