package com.example.meongnyangbook.shop.inquiry.inquiryComment;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.User;

public interface InquiryCommentService {

  /**
   * 문의 댓글 생성
   *
   * @param user
   * @param inquiryId
   * @param requestDto
   * @return
   */
  InquiryCommentResponseDto createInquiryComment(Long inquiryId, User user,
      InquiryCommentRequestDto requestDto);

  /**
   * 문의 댓글 수정
   *
   * @param user
   * @param inquiryId
   * @param requestDto
   * @return
   */

  InquiryCommentResponseDto updateInquiryComment(Long inquiryId, User user,
      InquiryCommentRequestDto requestDto);

  /**
   * 문의 댓글 삭제
   *
   * @param user
   * @param inquiryId
   * @return
   */
  ApiResponseDto deleteInquiryComment(Long inquiryId, User user);

  /**
   * 문의 댓글 정보 가져오기
   *
   * @param id
   * @return
   */
  InquiryComment getInquiryComment(Long id);
}
