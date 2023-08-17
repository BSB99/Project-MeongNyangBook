package com.example.meongnyangbook.post.community.service;

import com.example.meongnyangbook.dto.ApiResponseDto;
import com.example.meongnyangbook.post.community.dto.CommunityDetailResponseDto;
import com.example.meongnyangbook.post.dto.PostRequestDto;
import com.example.meongnyangbook.post.community.dto.CommunityResponseDto;
import com.example.meongnyangbook.post.community.entity.Community;
import com.example.meongnyangbook.post.community.repository.CommunityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityServiceImpl implements CommunityService {
    private CommunityRepository communityRepository;

    @Override
    public CommunityResponseDto createCommunity(PostRequestDto requestDto) {
        Community community =  new Community(requestDto);

        communityRepository.save(community);

        return new CommunityResponseDto(community);
    };

    @Override
    @Transactional
    public CommunityResponseDto updateCommunity(PostRequestDto requestDto, Long communityNo) {
        Community community = communityRepository.findById(communityNo).orElseThrow(() -> {
            throw new IllegalArgumentException("게시글이 존재하지 않습니다");
        });

        community.setTitle(requestDto.getTitle());
        community.setDescription(requestDto.getDescription());

        return new CommunityResponseDto(community);
    };

    @Override
    public ApiResponseDto deleteCommunity(Long communityNo) {
        Community community = getCommunity(communityNo);

        communityRepository.delete(community);

        return new ApiResponseDto("게시글 삭제가 완료되었습니다." ,200);
    }

    @Override
    public List<CommunityResponseDto> getCommunityList() {
        List<Community> communityList = communityRepository.findAllByOrderByCreatedAtDesc();

        return communityList.stream().map(CommunityResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public CommunityDetailResponseDto getOneCommunity(Long communityNo) {
        return new CommunityDetailResponseDto(getCommunity(communityNo));
    };

    @Override
    public Community getCommunity(Long communityNo) {
        return communityRepository.findById(communityNo).orElseThrow(() -> {
            throw new IllegalArgumentException("게시글이 존재하지 않습니다");
        });
    }
}
