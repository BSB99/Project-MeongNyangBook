package com.example.meongnyangbook.chat.repository;

import com.example.meongnyangbook.chat.entity.ChatRoom;
import com.example.meongnyangbook.user.entity.User;

public interface ChatRoomRepositoryQuery {
    ChatRoom findByConstructorAndParticipant(User constructor, User participant);
}
