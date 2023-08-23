package com.example.meongnyangbook.shop.item.controller;

import com.example.meongnyangbook.backoffice.notification.dto.NoticeRequestDto;
import com.example.meongnyangbook.backoffice.notification.dto.NoticeResponseDto;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.item.dto.ItemRequestDto;
import com.example.meongnyangbook.shop.item.dto.ItemResponseDto;
import com.example.meongnyangbook.shop.item.service.ItemService;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mya/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ApiResponseDto> createItem(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ItemRequestDto requestDto) {
        ApiResponseDto result = itemService.createItem(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> getItems(Pageable pageable) {
        List<ItemResponseDto> result = itemService.getItems(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{itemNo}")
    public ResponseEntity<ApiResponseDto> updateItem(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ItemRequestDto requestDto, @PathVariable Long itemNo) {
        ApiResponseDto result = itemService.updateItem(requestDto, itemNo);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{itemNo}")
    public ResponseEntity<ApiResponseDto> deleteItem(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long itemNo) {
        ApiResponseDto result = itemService.deleteItem(itemNo);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
