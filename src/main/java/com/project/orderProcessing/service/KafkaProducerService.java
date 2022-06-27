package com.project.orderProcessing.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class KafkaProducerService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

    @Value("${kafka.topic}")
    private String topic;

    public void sendMessage(String message)
    {
        try {
            kafkaTemplate.send(topic, message);
        }catch (Exception e){
            log.error("Failed to produce message to Kafka"+e);
            throw new RuntimeException(e);
        }
    }
}
