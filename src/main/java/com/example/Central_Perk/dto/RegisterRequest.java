package com.example.Central_Perk.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String role; // USER or ADMIN
}

