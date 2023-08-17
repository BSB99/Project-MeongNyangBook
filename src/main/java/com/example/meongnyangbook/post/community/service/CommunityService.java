package com.example.meongnyangbook.post.community.service;

import com.example.meongnyangbook.dto.ApiResponseDto;
import com.example.meongnyangbook.post.community.dto.CommunityDetailResponseDto;
import com.example.meongnyangbook.post.dto.PostRequestDto;
import com.example.meongnyangbook.post.community.dto.CommunityResponseDto;
import com.example.meongnyangbook.post.community.entity.Community;

import java.util.List;

public interface CommunityService {
    CommunityResponseDto createCommunity(PostRequestDto requestDto);

    CommunityResponseDto updateCommunity(PostRequestDto requestDto, Long communityNo);

    ApiResponseDto deleteCommunity(Long communityNo);

    Community getCommunity(Long communityNo);

    List<CommunityResponseDto> getCommunityList();

    CommunityDetailResponseDto getOneCommunity(Long communityNo);
}
