package com.project.orderProcessing.service;

import com.project.orderProcessing.model.MongoDetails;
import com.project.orderProcessing.model.OrderDetails;
import com.project.orderProcessing.repository.OrderProcessingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetService {

    @Autowired
    OrderProcessingRepository orderProcessingRepo;
    public OrderDetails getOrderProcessingDetails(String orderId){
        MongoDetails mongoDetails = orderProcessingRepo.findOrderById(orderId);
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(mongoDetails.getOrderId());
        orderDetails.setOrderQuantity(mongoDetails.getOrderQuantity());
        orderDetails.setOrderStatus(mongoDetails.getOrderStatus());
        orderDetails.setProductName(mongoDetails.getProductName());
        return orderDetails;
    }
}
