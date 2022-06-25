package com.project.orderProcessing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaProducerService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message)
    {
        kafkaTemplate.send("order_processing_topic", message);
    }
}
