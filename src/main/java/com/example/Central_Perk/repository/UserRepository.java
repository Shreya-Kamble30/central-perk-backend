package com.example.Central_Perk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Central_Perk.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

