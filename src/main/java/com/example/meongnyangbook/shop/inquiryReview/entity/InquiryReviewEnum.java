package com.example.meongnyangbook.shop.inquiryReview.entity;

public enum InquiryReviewEnum {
  INQUIRY,
  REVIEW;

  public static InquiryReviewEnum fromString(String value) {
    try {
      return InquiryReviewEnum.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid category name: " + value);
    }
  }
}
