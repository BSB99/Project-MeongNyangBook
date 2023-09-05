package com.example.meongnyangbook.shop.basket;

import lombok.Getter;

import java.util.List;

@Getter
public class BasketListResponseDto {
    private List<BasketResponseDto> basketList;

    public BasketListResponseDto(List<BasketResponseDto> basketResponseDto) {
        this.basketList = basketResponseDto;
    }
}
