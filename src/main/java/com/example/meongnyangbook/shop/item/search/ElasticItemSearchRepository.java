package com.example.meongnyangbook.shop.item.search;

import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticItemSearchRepository extends ElasticsearchRepository<ItemDocument, String>,
    CustomItemSearchRepository {

  List<ItemDocument> findByNameContaining(String keyword);

}
