package com.example.meongnyangbook.chat.repository;

import com.example.meongnyangbook.chat.entity.ChatRoom;
import com.example.meongnyangbook.user.User;

import java.util.List;

public interface ChatRoomRepositoryQuery {
    ChatRoom findByConstructorAndParticipant(User constructor, User participant);

    List<ChatRoom> findByUserChatRoom(User currentUser);
}
