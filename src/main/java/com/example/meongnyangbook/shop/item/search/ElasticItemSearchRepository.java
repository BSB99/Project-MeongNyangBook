package com.example.meongnyangbook.shop.item.search;

import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface ElasticItemSearchRepository extends ElasticsearchRepository<ItemDocument, String> {

  List<ItemDocument> findByNameContaining(String keyword);

  List<ItemDocument> findByNameContains(String keyword);

}
