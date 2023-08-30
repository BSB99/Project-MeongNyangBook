package com.example.meongnyangbook.shop.item.dto;

import com.example.meongnyangbook.post.entity.CategoryEnum;
import com.example.meongnyangbook.shop.item.entity.ItemCategoryEnum;
import lombok.Getter;

@Getter
public class ItemRequestDto {
    private String name;
    private Long price;
    private ItemCategoryEnum category;
    private String content;
}
