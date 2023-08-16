package com.example.meongnyangbook.chat.room.entity;

import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "message_rooms")
public class MessageRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_user")
    private User receiveUser;

    @ManyToOne
    @JoinColumn(name = "receive_user")
    private User sernderUser;
}
