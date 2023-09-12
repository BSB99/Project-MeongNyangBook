package com.example.meongnyangbook.post.communityPost;

import com.example.meongnyangbook.post.attachment.AttachmentUrlResponseDto;
import lombok.Getter;

@Getter
public class CommunityPostResponseDto {

  private Long id;
  private String title;
  private String description;
  private String createdAt;
  private String updatedAt;
  private Integer likeCount;
  private Integer commentCount;
  private AttachmentUrlResponseDto fileUrls;

  public CommunityPostResponseDto(CommunityPost communityPost) {
    this.id = communityPost.getId();
    this.title = communityPost.getTitle();
    this.description = communityPost.getDescription();
    this.createdAt = communityPost.getCreatedAtAsString();
    this.updatedAt = communityPost.getModifiedAtAsString();
    this.likeCount = communityPost.getPostLikes().size();
    this.commentCount = communityPost.getCommentList().size();
    this.fileUrls = new AttachmentUrlResponseDto(communityPost.getAttachmentUrl());
  }
}
