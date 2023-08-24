package com.example.meongnyangbook.shop.order.dto;

import com.example.meongnyangbook.shop.order.entity.Order;
import com.example.meongnyangbook.shop.order.entity.OrderItem;
import com.example.meongnyangbook.shop.order.entity.OrderStatusEnum;
import com.example.meongnyangbook.user.dto.OrderUserResponseDto;
import com.example.meongnyangbook.user.entity.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderResponseDto {
    private Long id;
    private Long totalPrice;
    private OrderStatusEnum status;
    private String reciverName;
    private String reciverPhoneNumber;
    private OrderUserResponseDto orderUser;
    private List<OrderItemResponseDto> orderItemList = new ArrayList<>();

    public OrderResponseDto(List<Order> order) {
        for (Order orderInfo : order) {
            this.id = orderInfo.getId();
            this.totalPrice = orderInfo.getTotalPrice();
            this.status = orderInfo.getStatusEnum();
            this.reciverName = orderInfo.getReciverName();
            this.reciverPhoneNumber = orderInfo.getReciverPhoneNumber();
            this.orderUser = new OrderUserResponseDto(orderInfo.getUser());
            for(OrderItem orderItem : orderInfo.getOrderItems()) {
                orderItemList.add(new OrderItemResponseDto(orderItem));
            }
        }
    }
}
