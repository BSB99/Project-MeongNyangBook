package com.example.meongnyangbook.shop.review;

import lombok.Getter;

@Getter
public class ReviewRequestDto {
    private String title;
    private String description;
    private Integer starRating;
}
