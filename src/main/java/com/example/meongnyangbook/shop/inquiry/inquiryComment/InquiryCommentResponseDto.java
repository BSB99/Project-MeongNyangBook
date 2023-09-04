package com.example.meongnyangbook.shop.inquiry.inquiryComment;

import lombok.Getter;

@Getter
public class InquiryCommentResponseDto {

  private Long commentId;
  private String contents;
  private Long inquiryId;
  private Long userId;
  private String nickname;
  private String createdAt;

  public InquiryCommentResponseDto(InquiryComment inquiryComment) {
    this.commentId = inquiryComment.getId();
    this.contents = inquiryComment.getContents();
    this.inquiryId = inquiryComment.getInquiry().getId();
    this.userId = inquiryComment.getUser().getId();
    this.nickname = inquiryComment.getUser().getNickname();
    this.createdAt = inquiryComment.getCreatedAtAsString();
  }
}
