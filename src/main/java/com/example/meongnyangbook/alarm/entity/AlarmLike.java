package com.example.meongnyangbook.alarm.entity;

import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "alarm_likes")
public class AlarmLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Column(name = "like_user", nullable = false)
    private String likeUser;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
