package com.example.meongnyangbook.shop.item.search;

import com.example.meongnyangbook.shop.item.ItemCategoryEnum;
import com.example.meongnyangbook.shop.item.dto.ItemRequestDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Document(indexName = "items")
@Getter
@NoArgsConstructor
public class ItemDocument {

  @Id
  private String id;

  private Long itemId;

  private String name;

  private Long price;

  private Long quantity = 10000L;

//  private String content;

  @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis,
      DateFormat.epoch_millis})
  private LocalDateTime createdAt;

  @Enumerated(value = EnumType.STRING)
  private ItemCategoryEnum category;

  private String attachmentItemUrl;


  public ItemDocument(Long itemId, LocalDateTime createdAt, ItemRequestDto requestDto,
      String attachmentItemUrl) {
    this.itemId = itemId;
    this.name = requestDto.getName();
    this.price = requestDto.getPrice();
    this.category = requestDto.getCategory();
    this.createdAt = createdAt;
    this.attachmentItemUrl = attachmentItemUrl;
  }

  @Builder
  public ItemDocument(Long itemId, String name, Long price, Long quantity,
      ItemCategoryEnum category, String attachmentItemUrl, LocalDateTime createdAt) {
    this.itemId = itemId;
    this.name = name;
    this.price = price;
    this.category = category;
    this.quantity = quantity;
    this.createdAt = createdAt;
    this.attachmentItemUrl = attachmentItemUrl;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public void setCategory(ItemCategoryEnum category) {
    this.category = category;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }
}

