package com.example.Central_Perk.dto;

import lombok.Data;

@Data
public class OrderRequest {

    private String orderItems;
    private Double totalAmount;
}
