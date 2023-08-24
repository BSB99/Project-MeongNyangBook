package com.example.meongnyangbook.shop.order.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.order.dto.OrderListResponseDto;
import com.example.meongnyangbook.shop.order.dto.OrderRequestDto;
import com.example.meongnyangbook.shop.order.dto.OrderResponseDto;
import com.example.meongnyangbook.user.entity.User;

import java.util.List;

public interface OrderService {
    ApiResponseDto createOrder(User user, OrderRequestDto requestDto);

    ApiResponseDto updateOrder(User user);

    OrderListResponseDto getOrderList(User user);
}
