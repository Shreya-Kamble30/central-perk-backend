package com.example.Central_Perk.controller;

import com.example.Central_Perk.dto.OrderRequest;
import com.example.Central_Perk.entity.Orders;
import com.example.Central_Perk.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    // Place Order
    @PostMapping("/place")
    public String placeOrder(@RequestBody OrderRequest request, Principal principal) {

        Orders order = new Orders();
        order.setUsername(principal.getName());
        order.setOrderItems(request.getOrderItems());
        order.setTotalAmount(request.getTotalAmount());

        orderRepository.save(order);

        return "Order placed successfully";
    }

    // Get previous orders
    @GetMapping("/my-orders")
    public List<Orders> getMyOrders(Principal principal) {
        return orderRepository.findByUsername(principal.getName());
    }


}
