package com.project.orderProcessing.service;

import com.project.orderProcessing.model.OrderDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessService {

    @Autowired
    KafkaProducerService kafkaProducerService;
    void orderProcessing(OrderDetails orderDetails){
        kafkaProducerService.sendMessage(orderDetails.toString());
    }
}
