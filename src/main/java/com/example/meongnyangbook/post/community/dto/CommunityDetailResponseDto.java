package com.example.meongnyangbook.post.community.dto;

import com.example.meongnyangbook.post.attachment.dto.AttachmentUrlResponseDto;
import com.example.meongnyangbook.post.comment.entity.Comment;
import com.example.meongnyangbook.post.community.entity.Community;
import com.example.meongnyangbook.post.dto.CommentResponseDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommunityDetailResponseDto {

  private Long id;
  private String title;
  private String description;
  private String createdAt;
  private String updatedAt;
  private Long viewCount;
  private String username;
  private List<CommentResponseDto> commentList = new ArrayList<>();
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
    for (Comment comment : community.getCommentList()) {
      commentList.add(new CommentResponseDto(comment));
    }
  }
}
