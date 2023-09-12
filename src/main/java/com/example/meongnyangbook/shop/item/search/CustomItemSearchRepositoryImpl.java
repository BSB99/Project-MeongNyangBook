package com.example.meongnyangbook.shop.item.search;

import com.example.meongnyangbook.shop.item.ItemCategoryEnum;
import java.util.List;
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
public class CustomItemSearchRepositoryImpl implements
    CustomItemSearchRepository {

  private final ElasticsearchOperations elasticsearchOperations;


  @Override
  public List<ItemDocument> searchByItem(String keyword, ItemCategoryEnum category,
      Long min,
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

    Query query = new CriteriaQuery(criteria).setPageable(pageable); // 페이징 처리

    SearchHits<ItemDocument> search = elasticsearchOperations.search(query,
        ItemDocument.class); // 조건 검색

    return search.stream()
        .map(SearchHit::getContent)
        .toList();

  }
}
