package com.example.meongnyangbook.chat.entity;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "chats")
public class Chat extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String message;

    public Chat(ChatRoom chatRoom, User user, String message) {
        this.chatRoom = chatRoom;
        this.user = user;
        this.message = message;
    }
}
