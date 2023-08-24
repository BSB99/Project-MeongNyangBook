package com.example.meongnyangbook.shop.order.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class OrderListResponseDto {
    private List<OrderResponseDto> orderList;

    public OrderListResponseDto(List<OrderResponseDto> orderResponseDto) {
        this.orderList = orderResponseDto;
    }
}
