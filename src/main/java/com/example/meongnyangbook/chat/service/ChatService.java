package com.example.meongnyangbook.chat.service;

import com.example.meongnyangbook.chat.dto.ChatMessageResponseDto;
import com.example.meongnyangbook.chat.dto.ChatRoomListResponseDto;
import com.example.meongnyangbook.chat.entity.Chat;
import com.example.meongnyangbook.chat.entity.ChatRoom;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.User;
import java.util.List;

public interface ChatService {

  /**
   * 채팅방 조회
   *
   * @param roomNo
   * @return
   */
  List<ChatMessageResponseDto> getChatMessages(Long roomNo);

  /**
   * 채팅 방 삭제
   *
   * @param roomNo
   * @return
   */
  ApiResponseDto deleteChatRoom(Long roomNo);

  /**
   * 채팅 방 생성
   *
   * @param userId
   * @param user
   * @return
   */
  ApiResponseDto createChatRoom(Long userId, User user);

  /**
   * 채팅 메세지 생성
   *
   * @param roomNo
   * @param userId
   * @param msg
   * @return
   */
  Chat createChat(Long roomNo, Long userId, String msg);

  /**
   * 채팅 방 리스트 조회
   *
   * @param user
   * @return
   */
  List<ChatRoomListResponseDto> getChatRooms(User user);

  ChatRoom getRoom(Long roomNo);
}
