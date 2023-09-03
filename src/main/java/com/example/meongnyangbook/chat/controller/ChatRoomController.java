package com.example.meongnyangbook.chat.controller;

import com.example.meongnyangbook.chat.dto.ChatMessageResponseDto;
import com.example.meongnyangbook.chat.dto.ChatRoomListResponseDto;
import com.example.meongnyangbook.chat.service.ChatServiceImpl;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mya/chats")
@Tag(name = "채팅방 조회 및 삭제")
@RequiredArgsConstructor
public class ChatRoomController {

  private final ChatServiceImpl chatService;

  @Operation(summary = "채팅방 메세지 조회")
  @GetMapping("/room/{roomNo}")
  public List<ChatMessageResponseDto> getChatMessages(@PathVariable Long roomNo) {
    return chatService.getChatMessages(roomNo);
  }

  @Operation(summary = "채팅방 삭제")
  @DeleteMapping("/room/{roomNo}")
  public ApiResponseDto deleteChatMessages(@PathVariable Long roomNo) {
    return chatService.deleteChatMessages(roomNo);
  }

  @Operation(summary = "채팅방 조회")
  @GetMapping("/rooms")
  public List<ChatRoomListResponseDto> getChatRooms(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return chatService.getChatRooms(userDetails.getUser());
  }
}
