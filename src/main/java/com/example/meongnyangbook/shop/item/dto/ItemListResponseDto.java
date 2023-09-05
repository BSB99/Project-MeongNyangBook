package com.example.meongnyangbook.shop.item.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class ItemListResponseDto {

  private int len;
  private List<ItemResponseDto> itemList;

  public ItemListResponseDto(List<ItemResponseDto> itemResponseDto, int len) {
    this.itemList = itemResponseDto;
    this.len = len;
  }

  public ItemListResponseDto(List<ItemResponseDto> itemResponseDto) {
    this.itemList = itemResponseDto;
  }
}
