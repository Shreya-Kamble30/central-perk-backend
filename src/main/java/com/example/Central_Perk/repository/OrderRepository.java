package com.example.Central_Perk.repository;

import com.example.Central_Perk.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUsername(String username);
}
