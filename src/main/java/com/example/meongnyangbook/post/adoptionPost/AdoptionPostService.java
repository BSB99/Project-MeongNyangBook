package com.example.meongnyangbook.post.adoptionPost;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.adoptionPost.dto.AdoptionPostDetailResponseDto;
import com.example.meongnyangbook.post.adoptionPost.dto.AdoptionPostPageResponseDto;
import com.example.meongnyangbook.post.adoptionPost.dto.AdoptionPostReqeustDto;
import com.example.meongnyangbook.post.adoptionPost.dto.AdoptionPostResponseDto;
import com.example.meongnyangbook.user.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


public interface AdoptionPostService {

  /**
   * 분양 게시물 생성
   *
   * @param user
   * @param reqeustDto
   * @return
   */
  ApiResponseDto createAdoption(User user, AdoptionPostReqeustDto reqeustDto,
      MultipartFile[] multipartFiles);


  /**
   * 분양 게시물 수정
   *
   * @param adoptionId
   * @param reqeustDto
   * @param multipartFiles
   * @return
   */
  AdoptionPostResponseDto updateAdoption(Long adoptionId, AdoptionPostReqeustDto reqeustDto,
      MultipartFile[] multipartFiles);

  /**
   * 분양 게시물 삭제
   *
   * @param adoptionId
   * @param user
   * @return
   */
  ApiResponseDto deleteAdoption(Long adoptionId, User user);

  /**
   * 분양 게시물 전체 조회
   *
   * @param pageable
   * @return
   */
  AdoptionPostPageResponseDto getAdoptionList(Pageable pageable);

  /**
   * 분양 게시물 단건 조회
   *
   * @param adoptionId
   * @param user
   * @return
   */
  AdoptionPostDetailResponseDto getSingleAdoption(Long adoptionId, User user);

  /**
   * 내가 작성한 분양 게시물 조회
   *
   * @param user
   * @return
   */
  List<AdoptionPostResponseDto> getMyAdoptionPostList(User user);

//  List<AdoptionResponseDto> searchAdoptionPostList(String keyword);

  /**
   * 분양 게시물 찾기
   *
   * @param adoptionId
   * @return
   */
  AdoptionPost getAdoption(Long adoptionId);

  List<AdoptionPostResponseDto> getRelativeAdoptionPostList(Long userNo);

  void adoptionCheck(User user, Long postId);
}
