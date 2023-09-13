package com.example.meongnyangbook.shop.item.search;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ElasticItemSearchRepositoryTest {

  @Autowired
  private ElasticItemSearchRepository repository;

  @Test
  public void testFindByNameContaining() {
    List<ItemDocument> items = repository.findByNameContaining("11");
    assertNotNull(items);
    assertFalse(items.isEmpty());
    items.forEach(item -> {
      assertTrue(item.getName().contains("11"));
    });
  }
}