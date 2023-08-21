package com.example.meongnyangbook.chat.repository;

import com.example.meongnyangbook.chat.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<Room, Long> {
}
