package com.example.meongnyangbook.backoffice.notification;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.UserDetailsImpl;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mya/backoffice/notices")
@RequiredArgsConstructor
public class NoticeController {

  private final NoticeService noticeService;

  @PostMapping
  public ResponseEntity<ApiResponseDto> createNotice(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody NoticeRequestDto requestDto) {
    ApiResponseDto result = noticeService.createNotice(requestDto);

    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @GetMapping
  public ResponseEntity<List<NoticeResponseDto>> getNotices(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    List<NoticeResponseDto> result = noticeService.getNotices();

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PutMapping("/{noticeNo}")
  public ResponseEntity<ApiResponseDto> updateNotice(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody NoticeRequestDto requestDto, @PathVariable Long noticeNo) {
    ApiResponseDto result = noticeService.updateNotice(requestDto, noticeNo);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @DeleteMapping("/{noticeNo}")
  public ResponseEntity<ApiResponseDto> deleteNotice(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long noticeNo) {
    ApiResponseDto result = noticeService.deleteNotice(noticeNo);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
