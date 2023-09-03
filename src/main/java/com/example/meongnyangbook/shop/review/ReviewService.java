package com.example.meongnyangbook.shop.review;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.User;
import java.util.List;

public interface ReviewService {

  /**
   * 리뷰쓰기
   *
   * @param user
   * @param requestDto
   * @param id
   * @return
   */
  ReviewResponseDto createReview(User user, ReviewRequestDto requestDto,
      Long id);

  /**
   * 해당 id 모든 리뷰 조회
   *
   * @param id
   * @return
   */
  List<ReviewResponseDto> getReviewList(Long id);

  /**
   * 리뷰 단건 조회
   *
   * @param id
   * @return
   */
  ReviewResponseDto getSingleReview(Long id);

  /**
   * 리뷰수정
   *
   * @param id
   * @param user
   * @param requestDto
   * @return
   */
  ReviewResponseDto updateReview(Long id, User user,
      ReviewRequestDto requestDto);

  /**
   * 리뷰 삭제
   *
   * @param id
   * @param user
   * @return
   */
  ApiResponseDto deleteReview(Long id, User user);

  /**
   * 리뷰 정보 가져오기
   *
   * @param id
   * @return
   */
  Review getReview(Long id);
}
