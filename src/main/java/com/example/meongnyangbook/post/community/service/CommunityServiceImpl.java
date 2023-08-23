package com.example.meongnyangbook.post.community.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.community.dto.CommunityDetailResponseDto;
import com.example.meongnyangbook.post.community.dto.CommunityResponseDto;
import com.example.meongnyangbook.post.community.entity.Community;
import com.example.meongnyangbook.post.community.repository.CommunityRepository;
import com.example.meongnyangbook.post.dto.PostRequestDto;
import com.example.meongnyangbook.redis.RedisUtil;
import com.example.meongnyangbook.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommunityServiceImpl implements CommunityService {

  private final CommunityRepository communityRepository;
  private final RedisUtil redisUtil;

  @Override
  public CommunityResponseDto createCommunity(PostRequestDto requestDto, User user) {
    Community community = new Community(requestDto, user);

    communityRepository.save(community);

    return new CommunityResponseDto(community);
  }

  ;

  @Override
  @Transactional
  public CommunityResponseDto updateCommunity(PostRequestDto requestDto, Long communityNo) {
    Community community = getCommunity(communityNo);

    //community.setTitle(requestDto.getTitle());
    //community.setDescription(requestDto.getDescription());

    return new CommunityResponseDto(community);
  }

  ;

  @Override
  public ApiResponseDto deleteCommunity(Long communityNo) {
    Community community = getCommunity(communityNo);

    communityRepository.delete(community);

    return new ApiResponseDto("게시글 삭제가 완료되었습니다.", 200);
  }

  @Override
  public List<CommunityResponseDto> getCommunityList(Pageable pageable) {
    Page<Community> communityList = communityRepository.findAllByOrderByCreatedAtDesc(pageable);

    return communityList.stream().map(CommunityResponseDto::new).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public CommunityDetailResponseDto getOneCommunity(Long communityNo, User user) {
    Community community = getCommunity(communityNo);

    String redisUserKey = user.getId().toString();
    Long viewCount = community.getViewCount();

    if (redisUtil.checkAndIncrementViewCount(communityNo.toString(), user.getId().toString())) {
//      redisUtil.incrementViewCount(communityNo.toString());
      // 조회수 증가
      Long newView = viewCount + 1;
      community.setViewCount(newView);

    }
    return new CommunityDetailResponseDto(community);
  }

  @Override
  public List<CommunityResponseDto> getMyCommunityPostList(User user) {
    return communityRepository.findByUserId(user.getId())
        .stream()
        .map(CommunityResponseDto::new)
        .toList();
  }

  ;

  @Override
  public Community getCommunity(Long communityNo) {
    return communityRepository.findById(communityNo).orElseThrow(() -> {
      throw new IllegalArgumentException("게시글이 존재하지 않습니다");
    });
  }
}
