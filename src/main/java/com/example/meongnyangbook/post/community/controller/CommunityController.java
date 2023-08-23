package com.example.meongnyangbook.post.community.controller;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.community.dto.CommunityDetailResponseDto;
import com.example.meongnyangbook.post.community.dto.CommunityResponseDto;
import com.example.meongnyangbook.post.community.service.CommunityService;
import com.example.meongnyangbook.post.dto.PostRequestDto;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "커뮤니티 API", description = "커뮤니티 포스트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/communities")
public class CommunityController {

  private final CommunityService communityService;

  @Operation(summary = "커뮤니티 포스트 등록")
  @PostMapping
  public CommunityResponseDto createCommunity(@RequestBody PostRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
    try {
      return communityService.createCommunity(requestDto, userDetails.getUser());
    } catch (Error error) {
      throw new Exception(error);
    }
  }

  @Operation(summary = "커뮤니티 전체조회(페이징)")
  @GetMapping("/page")
  public List<CommunityResponseDto> getCommunityList(Pageable pageable) throws Exception {
    try {
      return communityService.getCommunityList(pageable);
    } catch (Error error) {
      throw new Exception(error);
    }
  }

  @Operation(summary = "커뮤니티 단건 조회")
  @GetMapping("/{communityNo}")
  public CommunityDetailResponseDto getCommunity(@PathVariable Long communityNo,
      @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
    try {
      return communityService.getOneCommunity(communityNo, userDetails.getUser());
    } catch (Error error) {
      throw new Exception(error);
    }
  }

  @Operation(summary = "커뮤니티 포스트 수정")
  @PutMapping("/{communityNo}")
  public CommunityResponseDto updateCommunity(@PathVariable Long communityNo,
      @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto requestDto)
      throws Exception {
    try {
      return communityService.updateCommunity(requestDto, communityNo);
    } catch (Error error) {
      throw new Exception(error);
    }
  }

  @Operation(summary = "커뮤니티 포스트 삭제")
  @DeleteMapping("/{communityNo}")
  public ResponseEntity<ApiResponseDto> deleteCommunity(@PathVariable Long communityNo,
      @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
    try {
      ApiResponseDto result = communityService.deleteCommunity(communityNo);

      return ResponseEntity.status(HttpStatus.OK).body(result);
    } catch (Error error) {
      throw new Exception(error);
    }
  }

  // 내가 쓴 게시물 조회
  @GetMapping("/my-post")
  public ResponseEntity<List<CommunityResponseDto>> getMyCommunityPostList(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    List<CommunityResponseDto> result = communityService.getMyCommunityPostList(
        userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body(result);

  }
}
