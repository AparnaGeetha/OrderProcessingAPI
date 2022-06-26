package com.project.orderProcessing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KafkaProducerService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message)
    {
        kafkaTemplate.send("order-processing-topic", message);
    }
}
