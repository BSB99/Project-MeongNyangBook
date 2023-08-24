package com.example.meongnyangbook.shop.review.controller;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.review.dto.ReviewRequestDto;
import com.example.meongnyangbook.shop.review.dto.ReviewResponseDto;
import com.example.meongnyangbook.shop.review.service.ReviewService;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 게시물 등록")
    @PostMapping("/{itemId}")
    public ResponseEntity<ReviewResponseDto> createReview(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ReviewRequestDto requestDto, @PathVariable Long itemId) {
        ReviewResponseDto result = reviewService.createReview(userDetails.getUser(), requestDto, itemId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "댓글 게시물 조회")
    @GetMapping("/all/{itemId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewList(@PathVariable Long itemId) {
        List<ReviewResponseDto> result = reviewService.getReviewList(itemId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "댓글 게시글 단건 조회")
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getSingleReview(@PathVariable Long reviewId) {
        ReviewResponseDto result = reviewService.getSingleReview(reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "댓글 게시물 수정")
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ReviewRequestDto requestDto) {
        ReviewResponseDto result = reviewService.updateReview(reviewId, userDetails.getUser(), requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "댓글 게시물 삭제")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponseDto> deleteReview(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto result = reviewService.deleteReview(reviewId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
