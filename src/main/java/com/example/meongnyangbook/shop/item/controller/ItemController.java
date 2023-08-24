package com.example.meongnyangbook.shop.item.controller;

import com.example.meongnyangbook.backoffice.notification.dto.NoticeRequestDto;
import com.example.meongnyangbook.backoffice.notification.dto.NoticeResponseDto;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.item.dto.ItemRequestDto;
import com.example.meongnyangbook.shop.item.dto.ItemResponseDto;
import com.example.meongnyangbook.shop.item.service.ItemService;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "상품 API")
@RestController
@RequestMapping("/mya/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @Operation(summary = "상품 생성")
    @PostMapping
    public ResponseEntity<ApiResponseDto> createItem(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ItemRequestDto requestDto) {
        ApiResponseDto result = itemService.createItem(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "상품 목록 조회")
    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> getItems(Pageable pageable) {
        List<ItemResponseDto> result = itemService.getItems(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "상품 정보 수정")
    @PutMapping("/{itemNo}")
    public ResponseEntity<ApiResponseDto> updateItem(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ItemRequestDto requestDto, @PathVariable Long itemNo) {
        ApiResponseDto result = itemService.updateItem(requestDto, itemNo);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "상품 삭제")
    @DeleteMapping("/{itemNo}")
    public ResponseEntity<ApiResponseDto> deleteItem(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long itemNo) {
        ApiResponseDto result = itemService.deleteItem(itemNo);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
