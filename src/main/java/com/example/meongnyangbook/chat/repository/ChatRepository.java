package com.example.meongnyangbook.chat.repository;

import com.example.meongnyangbook.chat.entity.Chat;
import com.example.meongnyangbook.chat.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByRoom(Room room);

    void deleteByRoom(Room room);
}
