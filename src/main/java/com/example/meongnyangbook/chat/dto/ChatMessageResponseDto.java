package com.example.meongnyangbook.chat.dto;

import com.example.meongnyangbook.chat.entity.Chat;
import lombok.Getter;

@Getter
public class ChatMessageResponseDto {
    private String msg;
    private String sender;
    private String localTime;

    public ChatMessageResponseDto(Chat chat) {
        this.msg = chat.getMessage();
        this.sender = chat.getSender();
        this.localTime = chat.getCreatedAtAsString();
    }
}
