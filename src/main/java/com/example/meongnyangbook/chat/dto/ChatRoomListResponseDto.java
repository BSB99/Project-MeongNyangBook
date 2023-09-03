package com.example.meongnyangbook.chat.dto;

import com.example.meongnyangbook.chat.entity.ChatRoom;
import com.example.meongnyangbook.user.dto.ChatResponseDto;
import lombok.Getter;

@Getter
public class ChatRoomListResponseDto {
    private Long id;
    private ChatResponseDto participant;
    private ChatResponseDto constructor;

    public ChatRoomListResponseDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.participant = new ChatResponseDto(chatRoom.getGuestUserId());
        this.constructor = new ChatResponseDto(chatRoom.getHostUserId());
    }
}
