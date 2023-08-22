package com.example.meongnyangbook.post.community.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.community.dto.CommunityDetailResponseDto;
import com.example.meongnyangbook.post.dto.PostRequestDto;
import com.example.meongnyangbook.post.community.dto.CommunityResponseDto;
import com.example.meongnyangbook.post.community.entity.Community;
import com.example.meongnyangbook.user.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommunityService {
    CommunityResponseDto createCommunity(PostRequestDto requestDto, User user);

    CommunityResponseDto updateCommunity(PostRequestDto requestDto, Long communityNo);

    ApiResponseDto deleteCommunity(Long communityNo);

    Community getCommunity(Long communityNo);

    List<CommunityResponseDto> getCommunityList(Pageable pageable);

    CommunityDetailResponseDto getOneCommunity(Long communityNo);
}
