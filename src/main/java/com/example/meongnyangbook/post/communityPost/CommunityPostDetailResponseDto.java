package com.example.meongnyangbook.post.communityPost;

import com.example.meongnyangbook.post.attachment.AttachmentUrlResponseDto;
import com.example.meongnyangbook.post.comment.Comment;
import com.example.meongnyangbook.post.dto.CommentResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class CommunityPostDetailResponseDto {

  private Long id;
  private Long userId;
  private String title;
  private String description;
  private String createdAt;
  private String updatedAt;
  private Long viewCount;
  private String username;
  private List<CommentResponseDto> commentList = new ArrayList<>();
  private AttachmentUrlResponseDto fileUrls;


  public CommunityPostDetailResponseDto(CommunityPost communityPost, Double viewCount) {
    this.id = communityPost.getId();
    this.userId = communityPost.getUser().getId();
    this.title = communityPost.getTitle();
    this.description = communityPost.getDescription();
    this.createdAt = communityPost.getCreatedAtAsString();
    this.updatedAt = communityPost.getModifiedAtAsString();
    this.viewCount = viewCount.longValue();
    this.username = communityPost.getUser().getNickname();
    this.fileUrls = new AttachmentUrlResponseDto(communityPost.getAttachmentUrl());
    for (Comment comment : communityPost.getCommentList()) {
      commentList.add(new CommentResponseDto(comment));
    }
  }
}
