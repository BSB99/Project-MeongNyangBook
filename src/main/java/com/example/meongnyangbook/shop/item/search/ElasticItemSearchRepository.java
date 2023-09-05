package com.example.meongnyangbook.shop.item.search;

import com.example.meongnyangbook.shop.item.Item;
import java.util.Collection;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface ElasticItemSearchRepository extends ElasticsearchRepository<Item, Long> {

  List<Item> findByNameIn(Collection<String> keywords);

}
