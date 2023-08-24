package com.example.meongnyangbook.shop.inquiry.controller;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.inquiry.dto.InquiryRequestDto;
import com.example.meongnyangbook.shop.inquiry.dto.InquiryResponseDto;
import com.example.meongnyangbook.shop.inquiry.service.InquiryService;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/inquiries")
@Tag(name = "아이템 문의 API")
public class InquiryController {

    private final InquiryService inquiryService;

    @Operation(summary = "문의 게시물 등록")
    @PostMapping("/{itemId}")
    public ResponseEntity<InquiryResponseDto> createInquiry(@AuthenticationPrincipal
                                                            UserDetailsImpl userDetails, @RequestBody InquiryRequestDto requestDto,
                                                            @PathVariable Long itemId) {
        InquiryResponseDto result = inquiryService.createInquiry(
                userDetails.getUser(), requestDto, itemId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "문의 게시물 조회")
    @GetMapping("/all/{itemId}")
    public ResponseEntity<List<InquiryResponseDto>> getInquiryList(@PathVariable Long itemId) {
        List<InquiryResponseDto> result = inquiryService.getInquiryList(itemId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "문의 게시글 단건 조회")
    @GetMapping("/{inquiryId}")
    public ResponseEntity<InquiryResponseDto> getSingleInquiry(@PathVariable Long inquiryId) {
        InquiryResponseDto result = inquiryService.getSingleInquiry(inquiryId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "문의 게시물 수정")
    @PutMapping("/{inquiryId}")
    public ResponseEntity<InquiryResponseDto> updateInquiry(@PathVariable Long inquiryId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody InquiryRequestDto requestDto) {
        InquiryResponseDto result = inquiryService.updateInquiry(inquiryId, userDetails.getUser(), requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "문의 게시물 삭제")
    @DeleteMapping("/{inquiryId}")
    public ResponseEntity<ApiResponseDto> deleteInquiry(@PathVariable Long inquiryId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto result = inquiryService.deleteInquiry(inquiryId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
