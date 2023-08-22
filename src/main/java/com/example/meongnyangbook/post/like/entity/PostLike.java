package com.example.meongnyangbook.post.like.entity;

import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post_likes")
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @Builder
    public PostLike(User user,Post post){
        this.user=user;
        this.post=post;
    }
}
