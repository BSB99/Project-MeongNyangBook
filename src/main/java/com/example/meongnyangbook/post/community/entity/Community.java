package com.example.meongnyangbook.post.community.entity;

import com.example.meongnyangbook.post.dto.PostRequestDto;
import com.example.meongnyangbook.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "community_posts")
public class Community extends Post {
    public Community() {
        super();
    }
    public Community(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
    }
}
