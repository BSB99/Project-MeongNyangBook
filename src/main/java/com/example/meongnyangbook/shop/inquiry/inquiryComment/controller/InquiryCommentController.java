package com.example.meongnyangbook.shop.inquiry.inquiryComment.controller;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.inquiry.inquiryComment.dto.InquiryCommentRequestDto;
import com.example.meongnyangbook.shop.inquiry.inquiryComment.dto.InquiryCommentResponseDto;
import com.example.meongnyangbook.shop.inquiry.inquiryComment.service.InquiryCommentService;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "문의 답글 API")
@RequestMapping("/mya/inquiry-comment")
public class InquiryCommentController {

  private final InquiryCommentService inquiryCommentService;

  @PostMapping("/{inquiryId}")
  public ResponseEntity<InquiryCommentResponseDto> createInquiryComment(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long inquiryId,
      @RequestBody InquiryCommentRequestDto requestDto) {
    InquiryCommentResponseDto result = inquiryCommentService.createInquiryComment(inquiryId,
        userDetails.getUser(), requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @PatchMapping("/{commentId}")
  public ResponseEntity<InquiryCommentResponseDto> updateInquiryComment(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId,
      @RequestBody InquiryCommentRequestDto requestDto) {
    InquiryCommentResponseDto result = inquiryCommentService.updateInquiryComment(commentId,
        userDetails.getUser(), requestDto);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<ApiResponseDto> deleteInquiryComment(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
    ApiResponseDto result = inquiryCommentService.deleteInquiryComment(commentId,
        userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body(result);

  }

}
