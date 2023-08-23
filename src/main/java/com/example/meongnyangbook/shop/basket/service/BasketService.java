package com.example.meongnyangbook.shop.basket.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.basket.dto.BasketListResponseDto;
import com.example.meongnyangbook.shop.basket.dto.BasketResponseDto;
import com.example.meongnyangbook.shop.basket.entity.Basket;
import com.example.meongnyangbook.user.entity.User;

import java.util.List;

public interface BasketService {
    ApiResponseDto createBasket(User user, Long itemNo);

    BasketListResponseDto getBaskets(User user);

    ApiResponseDto deleteSingleBasket(User user, Long itemNo);

    ApiResponseDto deleteBasket(User user);
}
