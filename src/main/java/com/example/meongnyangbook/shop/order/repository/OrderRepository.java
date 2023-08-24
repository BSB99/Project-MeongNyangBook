package com.example.meongnyangbook.shop.order.repository;

import com.example.meongnyangbook.shop.order.entity.Order;
import com.example.meongnyangbook.shop.order.entity.OrderStatusEnum;
import com.example.meongnyangbook.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUserAndStatusEnum(User user, OrderStatusEnum orderStatusEnum);

    List<Order> findAllByUserAndStatusEnum(User user, OrderStatusEnum orderStatusEnum);
}
