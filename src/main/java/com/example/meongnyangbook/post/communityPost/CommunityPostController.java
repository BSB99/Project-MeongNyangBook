package com.example.meongnyangbook.post.communityPost;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.dto.PostRequestDto;
import com.example.meongnyangbook.user.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

@Tag(name = "커뮤니티 API", description = "커뮤니티 포스트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/communities")
@Slf4j(topic = "커뮤니티")
public class CommunityPostController {

  private final CommunityPostService communityPostService;

  @Operation(summary = "커뮤니티 포스트 등록")
  @PostMapping
  public ResponseEntity<ApiResponseDto> createCommunity(
      @RequestPart("requestDto") String requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestPart("fileName") MultipartFile[] multipartFiles) throws Exception {

    try {
      // 문자열 형태의 requestDto를 Java 객체로 역직렬화
      ObjectMapper objectMapper = new ObjectMapper();
      PostRequestDto postRequestDto = objectMapper.readValue(requestDto, PostRequestDto.class);
      ApiResponseDto result = communityPostService.createCommunity(postRequestDto,
          userDetails.getUser(),
          multipartFiles);

      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    } catch (Error error) {
      throw new Exception(error);
    }
  }

  @Operation(summary = "커뮤니티 전체조회(페이징)")
  @GetMapping
  public ResponseEntity<Page<CommunityPostResponseDto>> getCommunityList(Pageable pageable)
      throws Exception {
    Page<CommunityPostResponseDto> result = communityPostService.getCommunityList(pageable);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "커뮤니티 단건 조회")
  @GetMapping("/{communityNo}")
  public CommunityPostDetailResponseDto getCommunity(@PathVariable Long communityNo,
      @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
    try {
      return communityPostService.getOneCommunity(communityNo, userDetails.getUser());
    } catch (Error error) {
      throw new Exception(error);
    }
  }

  @Operation(summary = "커뮤니티 포스트 수정")
  @PutMapping(value = "/{communityNo}", consumes = {MediaType.APPLICATION_JSON_VALUE,
      MediaType.MULTIPART_FORM_DATA_VALUE})
//  , consumes = {"application/octet-stream",
//      "multipart/form-data"}
  public CommunityPostResponseDto updateCommunity(@PathVariable Long communityNo,
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestPart("requestDto") String requestDto,
      @RequestPart("fileName") MultipartFile[] multipartFiles
  ) throws Exception {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      PostRequestDto postRequestDto = objectMapper.readValue(requestDto, PostRequestDto.class);

//      String[] deleteDtos = deleteDto.split(",");
//      log.info(deleteDto);

//      DeleteDto deleteFile = objectMapper.readValue(deleteDto, DeleteDto.class);

      return communityPostService.updateCommunity(communityNo, postRequestDto, multipartFiles);
    } catch (Error error) {
      throw new Exception(error);
    }
  }

  @Operation(summary = "커뮤니티 포스트 삭제")
  @DeleteMapping("/{communityNo}")
  public ResponseEntity<ApiResponseDto> deleteCommunity(@PathVariable Long communityNo,
      @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
    try {
      ApiResponseDto result = communityPostService.deleteCommunity(communityNo);

      return ResponseEntity.status(HttpStatus.OK).body(result);

    } catch (Error error) {
      throw new Exception(error);
    }
  }

  // 내가 쓴 게시물 조회
  @GetMapping("/my-post")
  public ResponseEntity<List<CommunityPostResponseDto>> getMyCommunityPostList(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    List<CommunityPostResponseDto> result = communityPostService.getMyCommunityPostList(
        userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/relative-post/{userNo}")
  public ResponseEntity<List<CommunityPostResponseDto>> getRelativeCommunityPostList(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long userNo
  ) {
    List<CommunityPostResponseDto> result = communityPostService.getRelativeCommunityPostList(
        userNo);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
