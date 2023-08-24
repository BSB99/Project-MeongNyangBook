package com.example.meongnyangbook.shop.inquiryReview.dto;

import com.example.meongnyangbook.shop.inquiryReview.entity.InquiryReviewEnum;
import lombok.Getter;

@Getter
public class InquiryReviewRequestDto {

  private String title;
  private String description;
  private InquiryReviewEnum category;


}
