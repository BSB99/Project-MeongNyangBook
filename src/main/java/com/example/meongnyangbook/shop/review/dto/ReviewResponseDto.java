package com.example.meongnyangbook.shop.review.dto;

import com.example.meongnyangbook.shop.review.entity.Review;
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

    public ReviewResponseDto(Review review) {
        this.ReviewId = review.getId();
        this.title = review.getTitle();
        this.description = review.getDescription();
        this.starRating = review.getStarRating();
        this.userId = review.getUser().getId();
        this.nickname = review.getUser().getNickname();
        this.itemId = review.getItem().getId();
    }
}
