package com.example.meongnyangbook.chat.message.controller;

import com.example.meongnyangbook.chat.message.dao.ChatRoom;
import com.example.meongnyangbook.chat.message.dto.ChatRequestDto;
import com.example.meongnyangbook.chat.message.sercvice.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mya/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestBody ChatRequestDto requestDto) {
        return chatService.chatRoom(requestDto);
    }

    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRomms();
    }
}
