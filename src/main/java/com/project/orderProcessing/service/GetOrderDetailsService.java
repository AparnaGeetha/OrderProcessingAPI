package com.project.orderProcessing.service;

import com.project.orderProcessing.model.MongoDetails;
import com.project.orderProcessing.model.OrderDetails;
import com.project.orderProcessing.repository.OrderProcessingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

@Service
public class GetOrderDetailsService {

    @Autowired
    OrderProcessingRepository orderProcessingRepo;

    Logger log = LoggerFactory.getLogger(GetOrderDetailsService.class);

    public OrderDetails getOrderProcessingDetails(String orderId){
        log.info("Start: Getting order details");
        MongoDetails mongoDetails = new MongoDetails();
        try {
            mongoDetails = orderProcessingRepo.findOrderById(orderId);
        }catch (DataAccessResourceFailureException e){
            log.error("Failed to get order details from mongodb");
            throw new RuntimeException(e);
        }
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(mongoDetails.getOrderId());
        orderDetails.setOrderQuantity(mongoDetails.getOrderQuantity());
        orderDetails.setOrderStatus(mongoDetails.getOrderStatus());
        orderDetails.setProductName(mongoDetails.getProductName());
        return orderDetails;
    }
}
