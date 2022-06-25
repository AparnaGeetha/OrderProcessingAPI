package com.project.orderProcessing.service;

import com.project.orderProcessing.model.MongoDetails;
import com.project.orderProcessing.repository.OrderProcessingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderProcessingService {

    @Autowired
    OrderProcessingRepository orderProcessingRepo;

    public void updateOrderProcessingStatus(String orderId){
        MongoDetails mongoDetails = orderProcessingRepo.findOrderById(orderId);
        mongoDetails.setOrderStatus("processed");
        orderProcessingRepo.save(mongoDetails);
    }

}
