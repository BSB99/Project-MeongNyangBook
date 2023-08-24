package com.example.meongnyangbook.shop.basket.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BasketListResponseDto {
    private List<BasketResponseDto> basketList;

    public BasketListResponseDto(List<BasketResponseDto> basketResponseDto) {
        this.basketList = basketResponseDto;
    }
}
