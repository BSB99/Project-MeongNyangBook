package com.example.meongnyangbook.shop.inquiryReview.controller;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.inquiryReview.dto.InquiryReviewRequestDto;
import com.example.meongnyangbook.shop.inquiryReview.dto.InquiryReviewResponseDto;
import com.example.meongnyangbook.shop.inquiryReview.service.InquiryReviewService;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/inquiries/reviews")
public class InquiryReviewController {

  private final InquiryReviewService inquiryReviewService;

  @Operation(summary = "문의 & 리뷰 게시물 등록")
  @PostMapping("/{itemId}")
  public ResponseEntity<InquiryReviewResponseDto> createInquiryReview(@AuthenticationPrincipal
  UserDetailsImpl userDetails, @RequestBody InquiryReviewRequestDto requestDto,
      @PathVariable Long itemId) {
    InquiryReviewResponseDto result = inquiryReviewService.createInquiryReview(
        userDetails.getUser(), requestDto, itemId);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @Operation(summary = "문의, 리뷰 게시물 조회")
  @GetMapping("/all/{itemId}")
  public ResponseEntity<List<InquiryReviewResponseDto>> getInquiryReviewList(
      @RequestParam("category")
      String categoryName, @PathVariable Long itemId) {
    List<InquiryReviewResponseDto> result = inquiryReviewService.getInquiryReviewList(categoryName,
        itemId);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/{id}")
  public ResponseEntity<InquiryReviewResponseDto> getSingleInquiryReview(@PathVariable Long id) {
    InquiryReviewResponseDto result = inquiryReviewService.getSingleInquiryReview(id);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PutMapping("/{id}")
  public ResponseEntity<InquiryReviewResponseDto> updateInquiryReview(@PathVariable Long id,
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody InquiryReviewRequestDto requestDto) {
    InquiryReviewResponseDto result = inquiryReviewService.updateInquiryReview(id,
        userDetails.getUser(), requestDto);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponseDto> deleteInquiryReview(@PathVariable Long id,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    ApiResponseDto result = inquiryReviewService.deleteInquiryReview(id, userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
