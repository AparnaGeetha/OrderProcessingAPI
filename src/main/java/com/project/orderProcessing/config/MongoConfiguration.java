package com.project.orderProcessing.config;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfiguration {

    @Autowired
    MongoClient mongoClient;

    @Value("${spring.data.mongodb.database}")
    private String database;

    public MongoTemplate mongoTemplate() throws Exception{
        return new MongoTemplate(mongoClient, database);
    }
}
