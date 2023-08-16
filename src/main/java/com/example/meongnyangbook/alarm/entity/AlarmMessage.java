package com.example.meongnyangbook.alarm.entity;

import com.example.meongnyangbook.chat.room.entity.MessageRoom;
import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "alarm_messages")
public class AlarmMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "message_room_no")
    private MessageRoom messageRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
