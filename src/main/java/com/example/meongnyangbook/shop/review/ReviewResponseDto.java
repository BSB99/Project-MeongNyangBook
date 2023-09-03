package com.example.meongnyangbook.shop.review;

import lombok.Getter;

@Getter
public class ReviewResponseDto {

  private Long ReviewId;
  private String title;
  private String description;
  private Integer starRating;
  private Long userId;
  private String nickname;
  private Long itemId;
  private String createdAt;

  public ReviewResponseDto(Review review) {
    this.ReviewId = review.getId();
    this.title = review.getTitle();
    this.description = review.getDescription();
    this.starRating = review.getStarRating();
    this.userId = review.getUser().getId();
    this.nickname = review.getUser().getNickname();
    this.itemId = review.getItem().getId();
    this.createdAt = review.getCreatedAtAsString();
  }
}
