package com.project.orderProcessing.controller;

import com.project.orderProcessing.model.OrderDetails;
import com.project.orderProcessing.service.AddOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.orderProcessing.service.GetOrderDetailsService;

@RestController
public class OrderProcessingController {

    @Autowired
    GetOrderDetailsService getService;

    @Autowired
    AddOrderDetailsService addOrderDetailsService;

    @GetMapping(value="/get_order_details", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<OrderDetails> getOrderDetails(@RequestParam String orderId){
        OrderDetails orderDetails = getService.getOrderProcessingDetails(orderId);
        if (orderDetails!=null) new ResponseEntity(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @PostMapping("/add_order_details")
    void addOrderDetails(@RequestBody OrderDetails orderDetails){
        try{
            addOrderDetailsService.addOrderProcessingDetails(orderDetails);
            new ResponseEntity<OrderDetails>(HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            new ResponseEntity<OrderDetails>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
