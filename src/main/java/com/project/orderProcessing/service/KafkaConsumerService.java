package com.project.orderProcessing.service;

import org.springframework.beans.factory.annotation.Autowired;

public class KafkaConsumerService {

    @Autowired
    UpdateOrderProcessingService updateOrderProcessingService;

    void updateOrderProcessingStatusInMongo(){
        updateOrderProcessingService.updateOrderProcessingStatus("2");
    }

}
