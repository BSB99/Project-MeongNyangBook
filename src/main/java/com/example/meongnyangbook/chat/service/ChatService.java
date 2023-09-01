package com.example.meongnyangbook.chat.service;

import com.example.meongnyangbook.chat.dto.ChatMessageResponseDto;
import com.example.meongnyangbook.chat.dto.ChatRoomListResponseDto;
import com.example.meongnyangbook.chat.entity.Chat;
import com.example.meongnyangbook.chat.entity.ChatRoom;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.entity.User;

import java.util.List;

public interface ChatService {
    List<ChatMessageResponseDto> getChatMessages(Long roomNo);

    ApiResponseDto deleteChatMessages(Long roomNo);

    ChatRoom getRoom(Long roomNo);

    ApiResponseDto createChatRoom(Long userId, User user);

    Chat createChat(Long roomNo,Long userId, String msg);

    List<ChatRoomListResponseDto> getChatRooms(User user);
}
