package com.example.meongnyangbook.chat.service;

import com.example.meongnyangbook.chat.entity.Chat;
import com.example.meongnyangbook.chat.entity.Room;
import com.example.meongnyangbook.chat.repository.ChatRepository;
import com.example.meongnyangbook.chat.repository.ChatRoomRepository;
import com.example.meongnyangbook.common.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    public Chat createChat(Long roomNo, String sender, String msg) {
        Room chatRoom = getRoom(roomNo);
        System.out.println(sender);

        Chat chat = new Chat(chatRoom, sender, msg);

        chatRepository.save(chat);
        return chat;
    }

    public ApiResponseDto createChatRoom(String name) {
        Room room = new Room(name);

        chatRoomRepository.save(room);
        return new ApiResponseDto("채팅 방 생성 완료", 201);
    }

    public Room getRoom(Long roomNo) {
        return chatRoomRepository.findById(roomNo).orElseThrow(() -> {
            throw new IllegalArgumentException("채팅방이 존재하지 않습니다.");
        });
    }
}

