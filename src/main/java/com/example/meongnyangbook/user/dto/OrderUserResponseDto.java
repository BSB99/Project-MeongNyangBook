package com.example.meongnyangbook.user.dto;

import com.example.meongnyangbook.user.User;
import lombok.Getter;

@Getter
public class OrderUserResponseDto {
    private Long id;
    private String orderUser;

    public OrderUserResponseDto(User user) {
        this.id = user.getId();
        this.orderUser = user.getUsername();
    }
}
