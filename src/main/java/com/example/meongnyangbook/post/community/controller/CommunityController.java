package com.example.meongnyangbook.post.community.controller;

import com.example.meongnyangbook.dto.ApiResponseDto;
import com.example.meongnyangbook.post.community.dto.CommunityDetailResponseDto;
import com.example.meongnyangbook.post.community.dto.CommunityResponseDto;
import com.example.meongnyangbook.post.community.service.CommunityService;
import com.example.meongnyangbook.post.dto.PostRequestDto;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
@RequestMapping("/mya/communities")
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

    @GetMapping()
    public List<CommunityResponseDto> getCommunityList() throws Exception {
        try {
            return communityService.getCommunityList();
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
    public CommunityResponseDto createCommunity(@RequestBody PostRequestDto requestDto, @PathVariable Long communityNo) throws Exception {
        try {
            return communityService.updateCommunity(requestDto, communityNo);
        } catch (Error error) {
            throw new Exception(error);
        }
    }

    @DeleteMapping("/{communityNo}")
    public ResponseEntity<ApiResponseDto> deleteCommunity(@PathVariable Long communityNo) throws Exception {
        try {
            ApiResponseDto result = communityService.deleteCommunity(communityNo);

            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Error error) {
            throw new Exception(error);
        }
    }
}
