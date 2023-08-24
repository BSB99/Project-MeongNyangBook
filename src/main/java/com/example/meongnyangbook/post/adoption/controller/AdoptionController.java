package com.example.meongnyangbook.post.adoption.controller;


import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionDetailResponseDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionReqeustDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionResponseDto;
import com.example.meongnyangbook.post.adoption.service.AdoptionService;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "분양 포스트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/adoptions")
public class AdoptionController {

  private final AdoptionService adoptionService;

  @Operation(summary = "분양 페이지 포스트 등록")
  @PostMapping
  public ResponseEntity<AdoptionResponseDto> createAdoption(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestPart("requestDto") AdoptionReqeustDto reqeustDto,
      @RequestPart("fileName") MultipartFile[] multipartFiles) {
    AdoptionResponseDto result = adoptionService.createAdoption(userDetails.getUser(), reqeustDto,
        multipartFiles);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @Operation(summary = "분양 페이지 전체 조회")
  @GetMapping
  public ResponseEntity<List<AdoptionResponseDto>> getAdoptionList() {
    List<AdoptionResponseDto> result = adoptionService.getAdoptionList();
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "분양 페이지 단건 조회")
  @GetMapping("/{adoptionId}")
  public ResponseEntity<AdoptionDetailResponseDto> getSingleAdoption(
      @PathVariable Long adoptionId) {
    AdoptionDetailResponseDto result = adoptionService.getSingleAdoption(adoptionId);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "분양 페이지 포스트 수정")
  @PutMapping("/{adoptionId}")
  public ResponseEntity<AdoptionResponseDto> updateAdoption(@PathVariable Long adoptionId,
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody AdoptionReqeustDto reqeustDto) {
    AdoptionResponseDto result = adoptionService.updateAdoption(adoptionId, userDetails.getUser(),
        reqeustDto);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "분양 페이지 포스트 삭제")
  @DeleteMapping("/{adoptionId}")
  public ResponseEntity<ApiResponseDto> deleteAdoption(@PathVariable Long adoptionId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    ApiResponseDto result = adoptionService.deleteAdoption(adoptionId, userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  // 내가 쓴 게시물 조회
  @GetMapping("/my-post")
  public ResponseEntity<List<AdoptionResponseDto>> getMyAdoptionPostList(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    List<AdoptionResponseDto> result = adoptionService.getMyAdoptionPostList(userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
