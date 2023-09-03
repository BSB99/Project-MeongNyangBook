package com.example.meongnyangbook.chat.entity;

import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "chat_rooms")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guest_user_id")
    private User guestUserId;

    @ManyToOne
    @JoinColumn(name = "host_user_id")
    private User hostUserId;

    public ChatRoom(User guestUserId, User hostUserId) {
        this.guestUserId = guestUserId;
        this.hostUserId = hostUserId;
    }
}
