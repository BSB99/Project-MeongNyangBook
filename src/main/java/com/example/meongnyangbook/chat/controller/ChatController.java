package com.example.meongnyangbook.chat.controller;

import com.example.meongnyangbook.chat.dto.ChatRequestDto;
import com.example.meongnyangbook.chat.entity.Chat;
import com.example.meongnyangbook.chat.service.ChatServiceImpl;
import com.example.meongnyangbook.common.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatServiceImpl chatService;

    @MessageMapping("/{roomId}")
    @SendTo("/room/{roomId}")
    public Chat test(@DestinationVariable Long roomId, @RequestBody ChatRequestDto requestDto) {
        return chatService.createChat(roomId, requestDto.getSender(), requestDto.getMsg());
    }

    @PostMapping("/room")
    public ApiResponseDto createChatRoom(@RequestBody ChatRequestDto requestDto) {
        return chatService.createChatRoom(requestDto.getName());
    }
}
