package com.example.meongnyangbook.shop.item.search;

import java.util.List;
import lombok.Getter;

@Getter
public class ItemSearchListResponseDto {

  private Long len;
  private List<ItemSearchResponseDto> itemList;

  public ItemSearchListResponseDto(List<ItemSearchResponseDto> itemList, Long len) {
    this.itemList = itemList;
    this.len = len;
  }
}
