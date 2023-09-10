package com.example.meongnyangbook.shop.order.dto;

import lombok.Getter;

@Getter
public class OrderRequestDto {
    private String address;
    private String receiverName;
    private String receiverPhoneNumber;
    private String request;
}
