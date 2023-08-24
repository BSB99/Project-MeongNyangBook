package com.example.meongnyangbook.shop.order.repository;

import com.example.meongnyangbook.shop.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
