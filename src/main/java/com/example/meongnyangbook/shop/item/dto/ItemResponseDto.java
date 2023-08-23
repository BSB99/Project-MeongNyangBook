package com.example.meongnyangbook.shop.item.dto;

import com.example.meongnyangbook.shop.item.entity.Item;
import com.example.meongnyangbook.shop.item.entity.ItemCategoryEnum;
import lombok.Getter;

@Getter
public class ItemResponseDto {
    private final Long id;
    private final String name;
    private final Long price;
    private final ItemCategoryEnum category;
    private final String createdAt;

    public ItemResponseDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.category = item.getCategory();
        this.createdAt = item.getCreatedAtAsString();
    }
}