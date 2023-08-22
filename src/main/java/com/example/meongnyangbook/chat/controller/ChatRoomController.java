package com.example.meongnyangbook.chat.controller;

import com.example.meongnyangbook.chat.dto.ChatMessageResponseDto;
import com.example.meongnyangbook.chat.service.ChatServiceImpl;
import com.example.meongnyangbook.common.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mya/chats")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatServiceImpl chatService;
    @GetMapping("/room/{roomNo}")
    public List<ChatMessageResponseDto> getChatMessages(@PathVariable Long roomNo) {
        return chatService.getChatMessages(roomNo);
    }

    @DeleteMapping("/room/{roomNo}")
    public ApiResponseDto deleteChatMessages(@PathVariable Long roomNo) {
        return chatService.deleteChatMessages(roomNo);
    }
}
