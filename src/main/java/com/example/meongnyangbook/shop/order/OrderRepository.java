package com.example.meongnyangbook.shop.order;

import com.example.meongnyangbook.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUserAndStatusEnum(User user, OrderStatusEnum orderStatusEnum);

    List<Order> findAllByUserAndStatusEnum(User user, OrderStatusEnum orderStatusEnum);
}
