package com.example.meongnyangbook.backoffice.notification.controller;

import com.example.meongnyangbook.backoffice.notification.dto.NoticeRequestDto;
import com.example.meongnyangbook.backoffice.notification.dto.NoticeResponseDto;
import com.example.meongnyangbook.backoffice.notification.service.NoticeService;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mya/backoffice/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<ApiResponseDto> createNotice(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody NoticeRequestDto requestDto) {
        ApiResponseDto result = noticeService.createNotice(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<List<NoticeResponseDto>> getNotices(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<NoticeResponseDto> result = noticeService.getNotices();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{noticeNo}")
    public ResponseEntity<ApiResponseDto> updateNotice(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody NoticeRequestDto requestDto, @PathVariable Long noticeNo) {
        ApiResponseDto result = noticeService.updateNotice(requestDto, noticeNo);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{noticeNo}")
    public ResponseEntity<ApiResponseDto> deleteNotice(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long noticeNo) {
        ApiResponseDto result = noticeService.deleteNotice(noticeNo);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
