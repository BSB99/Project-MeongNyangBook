package com.example.meongnyangbook.shop.item.search;

import com.example.meongnyangbook.shop.item.ItemCategoryEnum;
import lombok.Getter;


@Getter
public class ItemSearchResponseDto {

  private final Long id;
  private final String name;
  private final Long price;
  private final ItemCategoryEnum category;
  //  private final String createdAt;
  private final Long quantity;


  public ItemSearchResponseDto(ItemDocument itemDocument) {
    this.id = itemDocument.getItemId();
    this.name = itemDocument.getName();
    this.price = itemDocument.getPrice();
    this.category = itemDocument.getCategory();
    this.quantity = itemDocument.getQuantity();
//    this.createdAt = itemDocument.getCreatedAt().toString();
  }
}

