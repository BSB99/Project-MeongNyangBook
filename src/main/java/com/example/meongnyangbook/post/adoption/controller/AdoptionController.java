package com.example.meongnyangbook.post.adoption.controller;


import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.adoption.service.AdoptionService;
import com.example.meongnyangbook.post.dto.AdoptionReqeustDto;
import com.example.meongnyangbook.post.dto.AdoptionResponseDto;
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
    public ResponseEntity<AdoptionResponseDto> createAdoption (@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody AdoptionReqeustDto reqeustDto) {
        AdoptionResponseDto result = adoptionService.createAdoption(userDetails.getUser(), reqeustDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<List<AdoptionResponseDto>> getAdoptionList() {
        List<AdoptionResponseDto> result = adoptionService.getAdoptionList();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{adoptionId}")
    public ResponseEntity<AdoptionResponseDto> getSingleAdoption(@PathVariable Long adoptionId) {
        AdoptionResponseDto result = adoptionService.getSingleAdoption(adoptionId);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{adoptionId}")
    public ResponseEntity<AdoptionResponseDto> updateAdoption(@PathVariable Long adoptionId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody AdoptionReqeustDto reqeustDto) {
        AdoptionResponseDto result = adoptionService.updateAdoption(adoptionId, userDetails.getUser(), reqeustDto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{adoptionId}")
    public ResponseEntity<ApiResponseDto> deleteAdoption(@PathVariable Long adoptionId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto result = adoptionService.deleteAdoption(adoptionId, userDetails.getUser());
        return ResponseEntity.ok().body(result);
    }

}
