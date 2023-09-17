package com.example.meongnyangbook.post.communityPost;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.dto.PostRequestDto;
import com.example.meongnyangbook.user.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface CommunityPostService {

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
  CommunityPostResponseDto updateCommunity(Long communityNo, PostRequestDto requestDto,
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
  Page<CommunityPostResponseDto> getCommunityList(Pageable pageable);

  /**
   * 커뮤니티 게시물 단건 조회
   *
   * @param communityNo
   * @param user
   * @return
   */
  CommunityPostDetailResponseDto getOneCommunity(Long communityNo, User user);

  /**
   * 내가 작성한 커뮤니티 게시물 조회
   *
   * @param user
   * @return
   */
  List<CommunityPostResponseDto> getMyCommunityPostList(User user);

  /**
   * 커뮤니티 게시물 찾기
   *
   * @param communityNo
   * @return
   */
  CommunityPost getCommunity(Long communityNo);

  List<CommunityPostResponseDto> getRelativeCommunityPostList(Long userNo);

  List<CommunityPostResponseDto> getBestCommunityPost();
}
