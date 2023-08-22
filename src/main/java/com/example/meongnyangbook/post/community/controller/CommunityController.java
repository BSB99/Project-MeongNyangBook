package com.example.meongnyangbook.post.community.controller;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.community.dto.CommunityDetailResponseDto;
import com.example.meongnyangbook.post.community.dto.CommunityResponseDto;
import com.example.meongnyangbook.post.community.service.CommunityService;
import com.example.meongnyangbook.post.dto.PostRequestDto;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/communities")
public class CommunityController {
    private final CommunityService communityService;

    @PostMapping
    public CommunityResponseDto createCommunity(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        try {
            return communityService.createCommunity(requestDto, userDetails.getUser());
        } catch (Error error) {
            throw new Exception(error);
        }
    }

    @GetMapping("/page")
    public List<CommunityResponseDto> getCommunityList(Pageable pageable) throws Exception {
        try {
            return communityService.getCommunityList(pageable);
        } catch (Error error) {
            throw new Exception(error);
        }
    }

    @GetMapping("/{communityNo}")
    public CommunityDetailResponseDto getCommunity(@PathVariable Long communityNo) throws Exception {
        try {
            return communityService.getOneCommunity(communityNo);
        } catch (Error error) {
            throw new Exception(error);
        }
    }

    @PutMapping("/{communityNo}")
    public CommunityResponseDto updateCommunity(@PathVariable Long communityNo, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto requestDto) throws Exception {
        try {
            return communityService.updateCommunity(requestDto, communityNo);
        } catch (Error error) {
            throw new Exception(error);
        }
    }

    @DeleteMapping("/{communityNo}")
    public ResponseEntity<ApiResponseDto> deleteCommunity(@PathVariable Long communityNo, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        try {
            ApiResponseDto result = communityService.deleteCommunity(communityNo);

            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Error error) {
            throw new Exception(error);
        }
    }
}
