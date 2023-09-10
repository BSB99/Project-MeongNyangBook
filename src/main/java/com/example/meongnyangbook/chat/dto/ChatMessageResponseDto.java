package com.example.meongnyangbook.chat.dto;

import com.example.meongnyangbook.chat.entity.Chat;
import com.example.meongnyangbook.user.dto.ChatResponseDto;
import lombok.Getter;

@Getter
public class ChatMessageResponseDto {
    private String msg;
    private String localTime;
    private ChatResponseDto responseDto;

    public ChatMessageResponseDto(Chat chat) {
        this.msg = chat.getMessage();
        this.localTime = chat.getCreatedAtAsString();
        this.responseDto = new ChatResponseDto(chat.getUser());
    }
}
