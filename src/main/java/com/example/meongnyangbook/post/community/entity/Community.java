package com.example.meongnyangbook.post.community.entity;

import com.example.meongnyangbook.post.dto.PostRequestDto;
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "community_posts")
public class Community extends Post {
    public Community(PostRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
        this.user = user;
    }
}
