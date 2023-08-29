package com.example.meongnyangbook.chat.service;

import com.example.meongnyangbook.chat.dto.ChatMessageResponseDto;
import com.example.meongnyangbook.chat.entity.Chat;
import com.example.meongnyangbook.chat.entity.ChatRoom;
import com.example.meongnyangbook.chat.repository.ChatRepository;
import com.example.meongnyangbook.chat.repository.ChatRoomRepository;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.entity.User;
import com.example.meongnyangbook.user.repository.UserRepository;
import com.example.meongnyangbook.user.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final UserService userService;

    @Override
    public Chat createChat(Long roomNo, User user, String msg) {
        ChatRoom chatRoom = getRoom(roomNo);

        Chat chat = new Chat(chatRoom, user, msg);

        chatRepository.save(chat);

        return chat;
    }

    @Override
    public ApiResponseDto createChatRoom(Long userId, User constructor) {
        User participant = userService.findUser(userId);
        ChatRoom findChatRoom = chatRoomRepository.findByConstructorAndParticipant(constructor, participant);

        if(findChatRoom != null) {
            throw new IllegalArgumentException("생성되 있는 채팅방입니다");
        }

        ChatRoom chatRoom = new ChatRoom(participant, constructor);
        chatRoomRepository.save(chatRoom);

        return new ApiResponseDto("채팅 방 생성 완료", 201);
    }

    @Override
    public ChatRoom getRoom(Long roomNo) {
        return chatRoomRepository.findById(roomNo).orElseThrow(() -> {
            throw new IllegalArgumentException("채팅방이 존재하지 않습니다.");
        });
    }

    @Override
    public List<ChatMessageResponseDto> getChatMessages(Long roomNo) {
        ChatRoom chatRoom = getRoom(roomNo);
        return chatRepository.findByChatRoom(chatRoom).stream().map(ChatMessageResponseDto::new).toList();
    }

    @Override
    public ApiResponseDto deleteChatMessages(Long roomNo) {
        ChatRoom chatRoom = getRoom(roomNo);
        chatRepository.deleteByChatRoom(chatRoom);
        return new ApiResponseDto("채팅방 제거", 200);
    }
}

