package com.example.meongnyangbook.shop.order;

import com.example.meongnyangbook.shop.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
