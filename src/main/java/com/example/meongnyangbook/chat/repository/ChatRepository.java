package com.example.meongnyangbook.chat.repository;

import com.example.meongnyangbook.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
