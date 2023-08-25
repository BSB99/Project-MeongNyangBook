package com.example.meongnyangbook.shop.inquiry.dto;

import com.example.meongnyangbook.shop.inquiry.entity.Inquiry;
import com.example.meongnyangbook.shop.inquiry.inquiryComment.dto.InquiryCommentResponseDto;
import java.util.Optional;
import lombok.Getter;

@Getter
public class InquiryResponseDto {

  private Long inquiryId;
  private String title;
  private String description;
  private Long userId;
  private String nickname;
  private Long itemId;
  private InquiryCommentResponseDto comment;
  private String createdAt;

  public InquiryResponseDto(Inquiry inquiry) {
    this.inquiryId = inquiry.getId();
    this.title = inquiry.getTitle();
    this.description = inquiry.getDescription();
    this.userId = inquiry.getUser().getId();
    this.nickname = inquiry.getUser().getNickname();
    this.itemId = inquiry.getItem().getId();
    this.comment = Optional.ofNullable(inquiry.getInquiryComment())
        .map(InquiryCommentResponseDto::new)
        .orElse(null);
    this.createdAt = inquiry.getCreatedAtAsString();
  }
}
