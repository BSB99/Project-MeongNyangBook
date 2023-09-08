package com.example.meongnyangbook.shop.review;

import lombok.Getter;

import java.util.List;

@Getter
public class ReviewListResponseDto {
    private Long len;
    private List<ReviewResponseDto> reviewList;

    public ReviewListResponseDto(List<ReviewResponseDto> reviewList, Long len) {
        this.reviewList = reviewList;
        this.len = len;
    }
}
