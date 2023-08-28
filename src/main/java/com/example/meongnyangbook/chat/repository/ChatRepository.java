package com.example.meongnyangbook.chat.repository;

import com.example.meongnyangbook.chat.entity.Chat;
import com.example.meongnyangbook.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByChatRoom(ChatRoom chatRoom);

    void deleteByChatRoom(ChatRoom chatRoom);
}
