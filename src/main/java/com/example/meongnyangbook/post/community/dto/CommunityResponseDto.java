package com.example.meongnyangbook.post.community.dto;

import com.example.meongnyangbook.post.community.entity.Community;
import lombok.Getter;

@Getter
public class CommunityResponseDto {
    private Long id;
    private String title;
    private String description;

    public CommunityResponseDto(Community community) {
        this.id = community.getId();
        this.title = community.getTitle();
        this.description = community.getDescription();
    }
}
