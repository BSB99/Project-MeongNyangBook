package com.example.meongnyangbook.shop.basket.repository;
import com.example.meongnyangbook.shop.basket.entity.Basket;
import com.example.meongnyangbook.shop.item.entity.Item;
import com.example.meongnyangbook.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    Basket findByUserAndItem(User user, Item item);
    List<Basket> findByUser(User user);
}
