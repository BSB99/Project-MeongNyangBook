package com.example.meongnyangbook.shop.inquiryReview.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.inquiryReview.dto.InquiryReviewRequestDto;
import com.example.meongnyangbook.shop.inquiryReview.dto.InquiryReviewResponseDto;
import com.example.meongnyangbook.shop.inquiryReview.entity.InquiryReview;
import com.example.meongnyangbook.user.entity.User;
import java.util.List;

public interface InquiryReviewService {

  InquiryReviewResponseDto createInquiryReview(User user, InquiryReviewRequestDto requestDto,
      Long id);

  List<InquiryReviewResponseDto> getInquiryReviewList(String categoryName, Long id);

  InquiryReviewResponseDto getSingleInquiryReview(Long id);

  InquiryReviewResponseDto updateInquiryReview(Long id, User user,
      InquiryReviewRequestDto requestDto);

  ApiResponseDto deleteInquiryReview(Long id, User user);

  InquiryReview getInquiryReview(Long id);

}
