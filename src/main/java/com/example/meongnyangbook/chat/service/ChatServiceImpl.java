package com.example.meongnyangbook.chat.service;

import com.example.meongnyangbook.chat.dto.ChatMessageResponseDto;
import com.example.meongnyangbook.chat.entity.Chat;
import com.example.meongnyangbook.chat.entity.Room;
import com.example.meongnyangbook.chat.repository.ChatRepository;
import com.example.meongnyangbook.chat.repository.ChatRoomRepository;
import com.example.meongnyangbook.common.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    @Override
    public Chat createChat(Long roomNo, String sender, String msg) {
        Room chatRoom = getRoom(roomNo);
        System.out.println(sender);

        Chat chat = new Chat(chatRoom, sender, msg);

        chatRepository.save(chat);
        return chat;
    }

    @Override
    public ApiResponseDto createChatRoom(String name) {
        Room room = new Room(name);

        chatRoomRepository.save(room);
        return new ApiResponseDto("채팅 방 생성 완료", 201);
    }

    @Override
    public Room getRoom(Long roomNo) {
        return chatRoomRepository.findById(roomNo).orElseThrow(() -> {
            throw new IllegalArgumentException("채팅방이 존재하지 않습니다.");
        });
    }

    @Override
    public List<ChatMessageResponseDto> getChatMessages(Long roomNo) {
        Room room = getRoom(roomNo);
        return chatRepository.findByRoom(room).stream().map(ChatMessageResponseDto::new).toList();
    }

    @Override
    public ApiResponseDto deleteChatMessages(Long roomNo) {
        Room room = getRoom(roomNo);
        chatRepository.deleteByRoom(room);
        return new ApiResponseDto("채팅방 제거", 200);
    }
}

