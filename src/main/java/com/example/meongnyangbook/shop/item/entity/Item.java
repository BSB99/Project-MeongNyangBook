package com.example.meongnyangbook.shop.item.entity;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.shop.item.dto.ItemRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "items")
public class Item extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    @Enumerated(value = EnumType.STRING)
    private ItemCategoryEnum category;

    public Item(ItemRequestDto requestDto) {
        this.name = requestDto.getName();
        this.price = requestDto.getPrice();
        this.category = requestDto.getCategory();
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
}
