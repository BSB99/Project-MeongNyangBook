package com.example.meongnyangbook.shop.order.dto;

import com.example.meongnyangbook.shop.item.dto.ItemResponseDto;
import com.example.meongnyangbook.shop.order.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemResponseDto {
    private Long id;
    private Integer itemCnt;
    private Long itemPrice;
    private Long totalPrice;
    private ItemResponseDto item;

    public OrderItemResponseDto(OrderItem orderItem){
        this.id = orderItem.getId();
        this.itemCnt = orderItem.getItemCnt();
        this.itemPrice = orderItem.getItemPrice();
        this.totalPrice = orderItem.getTotalPrice();
        this.item = new ItemResponseDto(orderItem.getItem());
    }
}
