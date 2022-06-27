package com.project.orderProcessing.service;

import com.project.orderProcessing.model.MongoDetails;
import com.project.orderProcessing.repository.OrderProcessingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderProcessingService {

    @Autowired
    OrderProcessingRepository orderProcessingRepo;

    Logger log = LoggerFactory.getLogger(UpdateOrderProcessingService.class);

    public void updateOrderProcessingStatus(String orderId){

        try {
            log.info("Start: updating order status");
            MongoDetails mongoDetails = orderProcessingRepo.findOrderById(orderId);
            mongoDetails.setOrderStatus("processed");
            orderProcessingRepo.save(mongoDetails);
            log.info("End: Order status updated successfully");
        } catch (Exception e) {
            log.error("Failed to update order status in mongodb");
            throw new RuntimeException(e);
        }
    }

}
