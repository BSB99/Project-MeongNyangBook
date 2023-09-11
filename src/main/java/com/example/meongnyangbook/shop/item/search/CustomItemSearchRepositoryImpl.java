package com.example.meongnyangbook.shop.item.search;

import com.example.meongnyangbook.shop.item.ItemCategoryEnum;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomItemSearchRepositoryImpl {

  private final ElasticsearchOperations elasticsearchOperations;


  public List<ItemDocument> searchByItem(String keyword, ItemCategoryEnum category, Long min,
      Long max,
      Pageable pageable) {
    Criteria criteria = new Criteria();

    if (keyword != null && !keyword.trim().isEmpty()) {
      criteria = criteria.and("name").contains(keyword);
    }

    if (category != null) {
      criteria = criteria.and("category").is(category.toString());
    }

    if (min != null) {
      criteria = criteria.and("price").greaterThanEqual(min);
    }

    if (max != null) {
      criteria = criteria.and("price").lessThanEqual(max);
    }

    Query query = new CriteriaQuery(criteria).setPageable(pageable);
    SearchHits<ItemDocument> search = elasticsearchOperations.search(query, ItemDocument.class);
    return search.stream()
        .map(SearchHit::getContent)
        .collect(Collectors.toList());
  }
}
