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

  CommunityResponseDto createCommunity(PostRequestDto requestDto, User user,
      MultipartFile[] multipartFiles);

  CommunityResponseDto updateCommunity(PostRequestDto requestDto, Long communityNo);

  ApiResponseDto deleteCommunity(Long communityNo);

  Community getCommunity(Long communityNo);

  List<CommunityResponseDto> getCommunityList(Pageable pageable);

  CommunityDetailResponseDto getOneCommunity(Long communityNo);

  List<CommunityResponseDto> getMyCommunityPostList(User user);

}
