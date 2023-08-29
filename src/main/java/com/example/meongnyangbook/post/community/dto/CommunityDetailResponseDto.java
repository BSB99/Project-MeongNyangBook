package com.example.meongnyangbook.post.community.dto;

import com.example.meongnyangbook.post.attachment.dto.AttachmentUrlResponseDto;
import com.example.meongnyangbook.post.community.entity.Community;
import lombok.Getter;

@Getter
public class CommunityDetailResponseDto {

  private Long id;
  private String title;
  private String description;
  private String createdAt;
  private String updatedAt;
  private Long viewCount;
  private String username;
  private AttachmentUrlResponseDto fileUrls;


  public CommunityDetailResponseDto(Community community, Double viewCount) {
    this.id = community.getId();
    this.title = community.getTitle();
    this.description = community.getDescription();
    this.createdAt = community.getCreatedAtAsString();
    this.updatedAt = community.getModifiedAtAsString();
    this.viewCount = viewCount.longValue();
    this.username = community.getUser().getNickname();
    this.fileUrls = new AttachmentUrlResponseDto(community.getAttachmentUrl());
  }
}
