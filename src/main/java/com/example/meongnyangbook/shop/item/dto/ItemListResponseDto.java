package com.example.meongnyangbook.shop.item.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ItemListResponseDto {
    private int len;
    private List<ItemResponseDto> itemList;

    public ItemListResponseDto(List<ItemResponseDto> itemResponseDto, int len) {
        this.itemList = itemResponseDto;
        this.len = len;
    }
}
