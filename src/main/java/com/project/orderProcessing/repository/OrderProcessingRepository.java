package com.project.orderProcessing.repository;

import com.project.orderProcessing.model.MongoDetails;
import com.project.orderProcessing.model.OrderDetails;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface OrderProcessingRepository extends MongoRepository<MongoDetails, String>  {

    @Query("{orderId:'?0'}")
    MongoDetails findOrderById(String orderId);

}
