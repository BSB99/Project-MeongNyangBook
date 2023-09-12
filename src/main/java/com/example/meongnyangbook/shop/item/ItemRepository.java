package com.example.meongnyangbook.shop.item;

import com.example.meongnyangbook.shop.item.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryQuery {
    List<Item> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Item> findAllByCategoryOrderByCreatedAtDesc(ItemCategoryEnum category, Pageable pageable);

    List<Item> findAllByCategory(ItemCategoryEnum category);
}
