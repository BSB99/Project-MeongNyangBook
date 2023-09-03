package com.example.meongnyangbook.chat.entity;

import com.example.meongnyangbook.user.User;
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
    @JoinColumn(name = "participant")
    private User participant;

    @ManyToOne
    @JoinColumn(name = "constructor")
    private User constructor;

    public ChatRoom(User participant, User constructor) {
        this.participant = participant;
        this.constructor = constructor;
    }
}
