package com.example.meongnyangbook.chat.repository;

import com.example.meongnyangbook.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryQuery {
}
