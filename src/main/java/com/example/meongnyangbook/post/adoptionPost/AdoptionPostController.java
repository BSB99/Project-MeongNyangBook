package com.example.meongnyangbook.post.adoptionPost;


import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.adoptionPost.dto.AdoptionPostDetailResponseDto;
import com.example.meongnyangbook.post.adoptionPost.dto.AdoptionPostPageResponseDto;
import com.example.meongnyangbook.post.adoptionPost.dto.AdoptionPostReqeustDto;
import com.example.meongnyangbook.post.adoptionPost.dto.AdoptionPostResponseDto;
import com.example.meongnyangbook.user.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "분양 포스트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/adoptions")
public class AdoptionPostController {

  private final AdoptionPostService adoptionPostService;

  @Operation(summary = "분양 페이지 포스트 등록")
  @PostMapping
  public ResponseEntity<ApiResponseDto> createAdoption(
          @AuthenticationPrincipal UserDetailsImpl userDetails,
          @RequestPart("requestDto") String requestDto,
          @RequestPart("fileName") MultipartFile[] multipartFiles) throws Exception {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      AdoptionPostReqeustDto adoptionPostReqeustDto = objectMapper.readValue(requestDto,
              AdoptionPostReqeustDto.class);

      ApiResponseDto result = adoptionPostService.createAdoption(userDetails.getUser(),
              adoptionPostReqeustDto,
              multipartFiles);
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    } catch (Error error) {
      throw new Exception(error);
    }
  }

  @Operation(summary = "분양 페이지 전체 조회(페이징)")
  @GetMapping
  public ResponseEntity<AdoptionPostPageResponseDto> getAdoptionList(Pageable pageable) {
    AdoptionPostPageResponseDto result = adoptionPostService.getAdoptionList(pageable);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "분양 페이지 단건 조회")
  @GetMapping("/{adoptionId}")
  public ResponseEntity<AdoptionPostDetailResponseDto> getSingleAdoption(
          @PathVariable Long adoptionId, @AuthenticationPrincipal UserDetailsImpl userDetails,
          Pageable commentPage) {
    AdoptionPostDetailResponseDto result = adoptionPostService.getSingleAdoption(adoptionId,
            userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "분양 페이지 포스트 수정")
  @PutMapping(value = "/{adoptionId}", consumes = {MediaType.APPLICATION_JSON_VALUE,
          MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<AdoptionPostResponseDto> updateAdoption(@PathVariable Long adoptionId,
                                                                @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                @RequestPart("requestDto") String requestDto,
                                                                @RequestPart("fileName") MultipartFile[] multipartFiles) throws Exception {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      AdoptionPostReqeustDto postRequestDto = objectMapper.readValue(requestDto,
              AdoptionPostReqeustDto.class);

      AdoptionPostResponseDto result = adoptionPostService.updateAdoption(adoptionId,
              postRequestDto,
              multipartFiles);
      return ResponseEntity.status(HttpStatus.OK).body(result);
    } catch (Error error) {
      throw new Exception(error);
    }
  }

  @Operation(summary = "분양 페이지 포스트 삭제")
  @DeleteMapping("/{adoptionId}")
  public ResponseEntity<ApiResponseDto> deleteAdoption(@PathVariable Long adoptionId,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {

    ApiResponseDto result = adoptionPostService.deleteAdoption(adoptionId, userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  // 내가 쓴 게시물 조회
  @GetMapping("/my-post")
  public ResponseEntity<List<AdoptionPostResponseDto>> getMyAdoptionPostList(
          @AuthenticationPrincipal UserDetailsImpl userDetails) {
    List<AdoptionPostResponseDto> result = adoptionPostService.getMyAdoptionPostList(
            userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/relative-post/{userNo}")
  public ResponseEntity<List<AdoptionPostResponseDto>> getRelativeAdoptionPostList(
          @PathVariable Long userNo) {
    List<AdoptionPostResponseDto> result = adoptionPostService.getRelativeAdoptionPostList(userNo);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

//  @GetMapping("/search")
//  public ResponseEntity<List<AdoptionResponseDto>> searchAdoptionPostList(
//      @RequestParam("keyword") String keyword) {
//    List<AdoptionResponseDto> result = adoptionService.searchAdoptionPostList(keyword);
//    return ResponseEntity.status(HttpStatus.OK).body(result);
//  }
}
