package com.example.meongnyangbook.chat.service;

import com.example.meongnyangbook.chat.dto.ChatMessageResponseDto;
import com.example.meongnyangbook.chat.entity.Chat;
import com.example.meongnyangbook.chat.entity.Room;
import com.example.meongnyangbook.common.ApiResponseDto;

import java.util.List;

public interface ChatService {
    List<ChatMessageResponseDto> getChatMessages(Long roomNo);

    ApiResponseDto deleteChatMessages(Long roomNo);

    Room getRoom(Long roomNo);

    ApiResponseDto createChatRoom(String name);

    Chat createChat(Long roomNo, String sender, String msg);
}
