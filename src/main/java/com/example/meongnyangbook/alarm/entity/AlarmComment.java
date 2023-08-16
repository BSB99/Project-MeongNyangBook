package com.example.meongnyangbook.alarm.entity;

import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "alarm_comments")
public class AlarmComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Column(name = "comment_content", nullable = false)
    private String commentContent;

    @Column(name = "comment_user", nullable = false)
    private String commentUser;

    @ManyToOne
    @JoinColumn(name = "like_user")
    private User user;
}
