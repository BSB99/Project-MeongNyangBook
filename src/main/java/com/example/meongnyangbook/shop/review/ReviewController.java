package com.example.meongnyangbook.shop.review;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/reviews")
@Tag(name = "아이템 리뷰 API")
public class ReviewController {

  private final ReviewService reviewService;

  @Operation(summary = "리뷰 등록")
  @PostMapping("/{itemId}")
  public ResponseEntity<ReviewResponseDto> createReview(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody ReviewRequestDto requestDto, @PathVariable Long itemId) {
    ReviewResponseDto result = reviewService.createReview(userDetails.getUser(), requestDto,
        itemId);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @Operation(summary = "리뷰 조회")
  @GetMapping("/all/{itemId}")
  public ResponseEntity<ReviewListResponseDto> getReviewList(@PathVariable Long itemId, Pageable pageable) {
    ReviewListResponseDto result = reviewService.getReviewList(itemId, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "리뷰 단건 조회")
  @GetMapping("/{reviewId}")
  public ResponseEntity<ReviewResponseDto> getSingleReview(@PathVariable Long reviewId) {
    ReviewResponseDto result = reviewService.getSingleReview(reviewId);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "리뷰 수정")
  @PutMapping("/{reviewId}")
  public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable Long reviewId,
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody ReviewRequestDto requestDto) {
    ReviewResponseDto result = reviewService.updateReview(reviewId, userDetails.getUser(),
        requestDto);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "리뷰 삭제")
  @DeleteMapping("/{reviewId}")
  public ResponseEntity<ApiResponseDto> deleteReview(@PathVariable Long reviewId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    ApiResponseDto result = reviewService.deleteReview(reviewId, userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
