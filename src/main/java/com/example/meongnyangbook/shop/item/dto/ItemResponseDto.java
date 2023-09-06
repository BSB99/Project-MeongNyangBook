package com.example.meongnyangbook.shop.item.dto;

import com.example.meongnyangbook.shop.item.Item;
import com.example.meongnyangbook.shop.item.ItemCategoryEnum;
import com.example.meongnyangbook.shop.item.search.ItemDocument;
import lombok.Getter;

@Getter
public class ItemResponseDto {

  private final Long id;
  private final String name;
  private final Long price;
  private final ItemCategoryEnum category;
  private final String createdAt;
  private final Long quantity;
//  private final S3FileItemResponseDto fileUrls;

  public ItemResponseDto(Item item) {
    this.id = item.getId();
    this.name = item.getName();
    this.price = item.getPrice();
    this.category = item.getCategory();
    this.quantity = item.getQuantity();
    this.createdAt = item.getCreatedAtAsString();
//    this.fileUrls = new S3FileItemResponseDto(item.getAttachmentItemUrl());
  }

  public ItemResponseDto(ItemDocument itemDocument) {
    this.id = itemDocument.getItemId();
    this.name = itemDocument.getName();
    this.price = itemDocument.getPrice();
    this.category = itemDocument.getCategory();
    this.quantity = itemDocument.getQuantity();
    this.createdAt = itemDocument.getCreatedAt().toString();
//    this.fileUrls = new S3FileItemResponseDto(itemDocument.getAttachmentItemUrl());
  }
}
