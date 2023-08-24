package com.example.meongnyangbook.shop.order.dto;

import com.example.meongnyangbook.shop.order.entity.Order;
import com.example.meongnyangbook.shop.order.entity.OrderItem;
import com.example.meongnyangbook.shop.order.entity.OrderStatusEnum;
import com.example.meongnyangbook.user.dto.OrderUserResponseDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderResponseDto {
    private Long id;
    private Long totalPrice;
    private OrderStatusEnum status;
    private String reciveName;
    private String recivePhoneNumber;
    private OrderUserResponseDto orderUser;
    private List<OrderItemResponseDto> orderItemList = new ArrayList<>();

    public OrderResponseDto(Order order) {
            this.id = order.getId();
            this.totalPrice = order.getTotalPrice();
            this.status = order.getStatusEnum();
            this.reciveName = order.getReciveName();
            this.recivePhoneNumber = order.getRecivePhoneNumber();
            this.orderUser = new OrderUserResponseDto(order.getUser());
            for(OrderItem orderItem : order.getOrderItems()) {
                orderItemList.add(new OrderItemResponseDto(orderItem));
        }
    }
}
