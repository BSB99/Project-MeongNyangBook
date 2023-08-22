package com.example.meongnyangbook.post.adoption.controller;


import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionDetailResponseDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionReqeustDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionResponseDto;
import com.example.meongnyangbook.post.adoption.service.AdoptionService;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/adoptions")
public class AdoptionController {
    private final AdoptionService adoptionService;

    @PostMapping
    public ResponseEntity<AdoptionResponseDto> createAdoption(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody AdoptionReqeustDto reqeustDto) {
        AdoptionResponseDto result = adoptionService.createAdoption(userDetails.getUser(), reqeustDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<List<AdoptionResponseDto>> getAdoptionList() {
        List<AdoptionResponseDto> result = adoptionService.getAdoptionList();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{adoptionId}")
    public ResponseEntity<AdoptionDetailResponseDto> getSingleAdoption(@PathVariable Long adoptionId) {
        AdoptionDetailResponseDto result = adoptionService.getSingleAdoption(adoptionId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{adoptionId}")
    public ResponseEntity<AdoptionResponseDto> updateAdoption(@PathVariable Long adoptionId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody AdoptionReqeustDto reqeustDto) {
        AdoptionResponseDto result = adoptionService.updateAdoption(adoptionId, userDetails.getUser(), reqeustDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{adoptionId}")
    public ResponseEntity<ApiResponseDto> deleteAdoption(@PathVariable Long adoptionId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto result = adoptionService.deleteAdoption(adoptionId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 내가 쓴 게시물 조회
    @GetMapping("/myPost")
    public List<AdoptionResponseDto> getMyAdoptionPostList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return adoptionService.getMyAdoptionPostList(userDetails.getUser());
    }
}
