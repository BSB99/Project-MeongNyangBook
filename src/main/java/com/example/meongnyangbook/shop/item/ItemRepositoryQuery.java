package com.example.meongnyangbook.shop.item;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ItemRepositoryQuery {
    List<Item> searchItemList(Long min, Long max, Pageable pageable, ItemCategoryEnum category);

    int searchItemListCnt(Long min, Long max, ItemCategoryEnum category);
}
