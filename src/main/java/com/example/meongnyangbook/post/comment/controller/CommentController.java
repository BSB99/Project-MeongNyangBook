package com.example.meongnyangbook.post.comment.controller;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.comment.service.CommentService;
import com.example.meongnyangbook.post.dto.CommentRequestDto;
import com.example.meongnyangbook.post.dto.CommentResponseDto;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/comments")
public class CommentController {

  private final CommentService commentService;

  @Operation(summary = "댓글 달기")
  @PostMapping
  public CommentResponseDto createComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody CommentRequestDto commentRequestDto) {
    return commentService.createComment(userDetails.getUser(), commentRequestDto);
  }

  @Operation(summary = "댓글 수정")
  @PatchMapping("/{commentId}")
  public CommentResponseDto updateComment(@PathVariable Long commentId,
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody CommentRequestDto commentRequestDto) {
    return commentService.updateComment(commentId, userDetails.getUser(), commentRequestDto);
  }

  @Operation(summary = "댓글 삭")
  @DeleteMapping("/{commentId}")
  public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long commentId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    ApiResponseDto result = commentService.deleteComment(commentId, userDetails.getUser());
    return ResponseEntity.ok().body(result);
  }
}
