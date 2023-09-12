package com.example.meongnyangbook.shop.item.search;

import com.example.meongnyangbook.shop.item.ItemCategoryEnum;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomItemSearchRepository {

  List<ItemDocument> searchByItem(String keyword, ItemCategoryEnum category, Long min,
      Long max, Pageable pageable);
}
