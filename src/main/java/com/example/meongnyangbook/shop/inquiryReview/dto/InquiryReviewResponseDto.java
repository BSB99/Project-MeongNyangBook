package com.example.meongnyangbook.shop.inquiryReview.dto;

import com.example.meongnyangbook.shop.inquiryReview.entity.InquiryReview;
import lombok.Getter;

@Getter
public class InquiryReviewResponseDto {

  private Long inquiryReviewId;
  private String title;
  private String description;
  private Long userId;
  private Long itemId;

  public InquiryReviewResponseDto(InquiryReview inquiryReview) {
    this.inquiryReviewId = inquiryReview.getId();
    this.title = inquiryReview.getTitle();
    this.description = inquiryReview.getDescription();
    this.userId = inquiryReview.getUser().getId();
    this.itemId = inquiryReview.getItem().getId();
  }
}
