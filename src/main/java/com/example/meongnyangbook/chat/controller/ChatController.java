package com.example.meongnyangbook.chat.controller;

import com.example.meongnyangbook.chat.dto.ChatRequestDto;
import com.example.meongnyangbook.chat.entity.Chat;
import com.example.meongnyangbook.chat.service.ChatServiceImpl;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "채팅API")
@RequiredArgsConstructor
public class ChatController {

  private final ChatServiceImpl chatService;

  @Operation(summary = "채팅방 접속 겸 채팅 보내")
  @MessageMapping("/{roomId}")
  @SendTo("/room/{roomId}")
  public Chat test(@DestinationVariable Long roomId, @RequestBody ChatRequestDto requestDto) {
    return chatService.createChat(roomId, requestDto.getUserId(), requestDto.getMsg());
  }

  @Operation(summary = "채팅방 생성")
  @PostMapping("/room/user/{userId}")
  public ApiResponseDto createChatRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long userId) {
    return chatService.createChatRoom(userId, userDetails.getUser());
  }
}
