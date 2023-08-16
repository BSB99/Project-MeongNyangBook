package com.example.meongnyangbook.post.community.controller;

import com.example.meongnyangbook.dto.ApiResponseDto;
import com.example.meongnyangbook.post.community.dto.CommunityResponseDto;
import com.example.meongnyangbook.post.community.service.CommunityService;
import com.example.meongnyangbook.post.dto.PostRequestDto;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@NoArgsConstructor
@RequestMapping("/mya/community")
public class CommunityController {
    private CommunityService communityService;

    @PostMapping()
    public CommunityResponseDto createCommunity(@RequestBody PostRequestDto requestDto) throws Exception {
        try {
            return communityService.createCommunity(requestDto);
        } catch (Error error) {
            throw new Exception(error);
        }
    }

    @PutMapping("/{communityNo}")
    public CommunityResponseDto createCommunity(@RequestBody PostRequestDto requestDto, @PathVariable Long communityNo) throws Exception {
        try {
            return communityService.updateCommunity(requestDto, communityNo);
        } catch (Error error) {
            throw new Exception(error);
        }
    }

    @DeleteMapping("/{communityNo}")
    public ApiResponseDto deleteCommunity(@PathVariable Long communityNo) throws Exception {
        try {
            return communityService.deleteCommunity(communityNo);
        } catch (Error error) {
            throw new Exception(error);
        }
    }
}
