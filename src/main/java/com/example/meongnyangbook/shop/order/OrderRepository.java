package com.example.meongnyangbook.shop.order;

import com.example.meongnyangbook.shop.order.dto.OrderResponseDto;
import com.example.meongnyangbook.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);

    Order findByUserAndId(User user, Long id);
}
