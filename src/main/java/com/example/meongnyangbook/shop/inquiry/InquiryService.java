package com.example.meongnyangbook.shop.inquiry;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.User;
import java.util.List;


public interface InquiryService {

  /**
   * 문의 하기
   *
   * @param user
   * @param requestDto
   * @param id
   * @return
   */
  InquiryResponseDto createInquiry(User user, InquiryRequestDto requestDto,
      Long id);

  /**
   * 문의 전체 조회
   *
   * @param id
   * @return
   */
  List<InquiryResponseDto> getInquiryList(Long id);

  /**
   * 문의 상세 조회
   *
   * @param id
   * @return
   */
  InquiryResponseDto getSingleInquiry(Long id);

  /**
   * 문의 수정
   *
   * @param user
   * @param id
   * @param requestDto
   * @return
   */
  InquiryResponseDto updateInquiry(Long id, User user,
      InquiryRequestDto requestDto);

  /**
   * 문의 삭제
   *
   * @param user
   * @param id
   * @return
   */
  ApiResponseDto deleteInquiry(Long id, User user);

  /**
   * 문의 정보 찾아서 가져오기
   *
   * @param id
   * @return
   */
  Inquiry getInquiry(Long id);
}
