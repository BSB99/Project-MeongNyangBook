package com.example.meongnyangbook.shop.basket.repository;
import com.example.meongnyangbook.shop.basket.Basket;
import com.example.meongnyangbook.shop.item.Item;
import com.example.meongnyangbook.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long>, BasketRepositoryQuery {
    Basket findByUserAndItem(User user, Item item);
    List<Basket> findByUser(User user);
}
