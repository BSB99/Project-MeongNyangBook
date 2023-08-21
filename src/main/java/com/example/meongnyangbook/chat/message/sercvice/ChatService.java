package com.example.meongnyangbook.chat.message.sercvice;

import com.example.meongnyangbook.chat.message.dao.ChatRoom;
import com.example.meongnyangbook.chat.message.dto.ChatRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRomms() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(String roomid) {
        return chatRooms.get(roomid);
    }

    // 고유한 uuid를 이용하여 채팅방 생성
    public ChatRoom chatRoom(ChatRequestDto requestDto) {
        String randomid = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomid)
                .name(requestDto.getName())
                .build();
        chatRooms.put(randomid, chatRoom);
        return chatRoom;
    }

    // 메세지 전송
    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
           session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }
}
