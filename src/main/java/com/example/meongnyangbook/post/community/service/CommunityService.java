package com.example.meongnyangbook.post.community.service;

import com.example.meongnyangbook.dto.ApiResponseDto;
import com.example.meongnyangbook.post.dto.PostRequestDto;
import com.example.meongnyangbook.post.community.dto.CommunityResponseDto;
import com.example.meongnyangbook.post.community.entity.Community;

public interface CommunityService {
    CommunityResponseDto createCommunity(PostRequestDto requestDto);

    CommunityResponseDto updateCommunity(PostRequestDto requestDto, Long communityNo);

    ApiResponseDto deleteCommunity(Long communityNo);

    Community getCommunity(Long communityNo);
}
