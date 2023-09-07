package com.example.meongnyangbook.post.like;


import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "좋아요 API")
@RequiredArgsConstructor
@RequestMapping("/mya/likes")
public class LikeController {

  private final LikeService likeService;

  @Operation(summary = "좋아요 등록")
  @PostMapping("/{postId}")
  public ResponseEntity<ApiResponseDto> createPostLike(@PathVariable Long postId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    ApiResponseDto result = likeService.createPostLike(postId, userDetails.getUser());
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @Operation(summary = "좋아요 취소")
  @DeleteMapping("/{postId}")
  public ResponseEntity<ApiResponseDto> deletePostLike(@PathVariable Long postId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    ApiResponseDto result = likeService.deletePostLike(postId, userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "좋아요 리스트")
  @GetMapping("/{postId}")
  public ResponseEntity<Boolean> confirmPostLike(@PathVariable Long postId,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
    Boolean result = likeService.confirmPostLike(postId, userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
