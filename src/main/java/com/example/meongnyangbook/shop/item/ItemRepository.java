package com.example.meongnyangbook.shop.item;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemRepository extends JpaRepository<Item, Long> {

  List<Item> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
