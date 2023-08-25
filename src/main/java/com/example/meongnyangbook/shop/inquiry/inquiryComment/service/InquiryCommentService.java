package com.example.meongnyangbook.shop.inquiry.inquiryComment.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.inquiry.inquiryComment.dto.InquiryCommentRequestDto;
import com.example.meongnyangbook.shop.inquiry.inquiryComment.dto.InquiryCommentResponseDto;
import com.example.meongnyangbook.shop.inquiry.inquiryComment.entity.InquiryComment;
import com.example.meongnyangbook.user.entity.User;

public interface InquiryCommentService {

    InquiryCommentResponseDto createInquiryComment(Long inquiryId, User user, InquiryCommentRequestDto requestDto);

    InquiryCommentResponseDto updateInquiryComment(Long inquiryId, User user, InquiryCommentRequestDto requestDto);

    ApiResponseDto deleteInquiryComment(Long inquiryId, User user);

    InquiryComment getInquiryComment(Long id);
}
