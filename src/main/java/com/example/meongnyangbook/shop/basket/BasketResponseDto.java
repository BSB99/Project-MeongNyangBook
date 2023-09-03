package com.example.meongnyangbook.shop.basket;

import com.example.meongnyangbook.shop.item.dto.ItemResponseDto;
import lombok.Getter;

@Getter
public class BasketResponseDto {
    private Long id;
    private int cnt;
    private Long totalPrice;
    private ItemResponseDto item;

    public BasketResponseDto(Basket basket) {
        this.id = basket.getId();
        this.cnt = basket.getCnt();
        this.totalPrice = basket.getCnt() * basket.getItem().getPrice();
        this.item = new ItemResponseDto(basket.getItem());
    }
}
