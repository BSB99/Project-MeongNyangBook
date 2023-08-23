package com.example.meongnyangbook.post.adoption.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionDetailResponseDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionReqeustDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionResponseDto;
import com.example.meongnyangbook.post.adoption.entity.Adoption;
import com.example.meongnyangbook.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Pageable;


public interface AdoptionService {

  /**
   * 분양 게시물 생성
   *
   * @param user
   * @param reqeustDto
   * @return
   */
  AdoptionResponseDto createAdoption(User user, AdoptionReqeustDto reqeustDto);

  /**
   * 분양 게시물 수정
   *
   * @param adoptionId
   * @param user
   * @param reqeustDto
   * @return
   */
  AdoptionResponseDto updateAdoption(Long adoptionId, User user, AdoptionReqeustDto reqeustDto);

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
  List<AdoptionResponseDto> getAdoptionList(Pageable pageable);

  /**
   * 분양 게시물 단건 조회
   *
   * @param adoptionId
   * @param user
   * @return
   */
  AdoptionDetailResponseDto getSingleAdoption(Long adoptionId, User user);

  /**
   * 내가 작성한 분양 게시물 조회
   *
   * @param user
   * @return
   */
  List<AdoptionResponseDto> getMyAdoptionPostList(User user);

  /**
   * 분양 베스트 게시물 조회
   *
   * @return
   */
  AdoptionResponseDto getBestAdoptionPost();

  /**
   * 분양 게시물 찾기
   *
   * @param adoptionId
   * @return
   */
  Adoption getAdoption(Long adoptionId);
}
