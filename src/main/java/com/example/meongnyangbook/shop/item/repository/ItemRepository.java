package com.example.meongnyangbook.shop.item.repository;

import com.example.meongnyangbook.shop.item.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
