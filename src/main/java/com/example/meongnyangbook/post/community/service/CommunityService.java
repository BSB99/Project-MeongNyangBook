package com.example.meongnyangbook.post.community.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.community.dto.CommunityDetailResponseDto;
import com.example.meongnyangbook.post.community.dto.CommunityResponseDto;
import com.example.meongnyangbook.post.community.entity.Community;
import com.example.meongnyangbook.post.dto.PostRequestDto;
import com.example.meongnyangbook.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface CommunityService {

  /**
   * 커뮤니티 게시물 생성
   *
   * @param requestDto
   * @param user
   * @return
   */
  ApiResponseDto createCommunity(PostRequestDto requestDto, User user,
      MultipartFile[] multipartFiles);

  /**
   * 커뮤니티 게시물 수정
   *
   * @param requestDto
   * @param communityNo
   * @return
   */
  CommunityResponseDto updateCommunity(Long communityNo, PostRequestDto requestDto,
      MultipartFile[] multipartFiles);

  /**
   * 커뮤니티 게시물 삭제
   *
   * @param communityNo
   * @return
   */
  ApiResponseDto deleteCommunity(Long communityNo);

  /**
   * 커뮤니티 게시물 전체 조회
   *
   * @param pageable
   * @return
   */
  List<CommunityResponseDto> getCommunityList(Pageable pageable);

  /**
   * 커뮤니티 게시물 단건 조회
   *
   * @param communityNo
   * @param user
   * @return
   */
  CommunityDetailResponseDto getOneCommunity(Long communityNo, User user);

  /**
   * 커뮤니티 베스트 게시물 조회
   *
   * @return
   */
  CommunityResponseDto getBestAdoptionPost();

  /**
   * 내가 작성한 커뮤니티 게시물 조회
   *
   * @param user
   * @return
   */
  List<CommunityResponseDto> getMyCommunityPostList(User user);

  /**
   * 커뮤니티 게시물 찾기
   *
   * @param communityNo
   * @return
   */
  Community getCommunity(Long communityNo);

    List<CommunityResponseDto> getRelativeCommunityPostList(Long userNo);
}
