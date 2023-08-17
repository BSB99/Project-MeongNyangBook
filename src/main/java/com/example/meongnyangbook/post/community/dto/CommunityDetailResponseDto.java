package com.example.meongnyangbook.post.community.dto;

import com.example.meongnyangbook.post.community.entity.Community;
import lombok.Getter;

@Getter
public class CommunityDetailResponseDto {
    private Long id;
    private String title;
    private String description;
    private String createdAt;
    private String updatedAt;

    public CommunityDetailResponseDto(Community community) {
        this.id = community.getId();
        this.title = community.getTitle();
        this.description = community.getDescription();
        this.createdAt = community.getCreatedAtAsString();
        this.updatedAt = community.getModifiedAtAsString();
    }
}
