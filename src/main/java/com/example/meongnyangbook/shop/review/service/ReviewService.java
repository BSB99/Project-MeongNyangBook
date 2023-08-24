package com.example.meongnyangbook.shop.review.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.review.dto.ReviewRequestDto;
import com.example.meongnyangbook.shop.review.dto.ReviewResponseDto;
import com.example.meongnyangbook.shop.review.entity.Review;
import com.example.meongnyangbook.user.entity.User;

import java.util.List;

public interface ReviewService {

    ReviewResponseDto createReview(User user, ReviewRequestDto requestDto,
                                   Long id);

    List<ReviewResponseDto> getReviewList(Long id);

    ReviewResponseDto getSingleReview(Long id);

    ReviewResponseDto updateReview(Long id, User user,
                                   ReviewRequestDto requestDto);

    ApiResponseDto deleteReview(Long id, User user);

    Review getReview(Long id);
}
