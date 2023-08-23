package com.example.meongnyangbook.shop.basket.controller;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.basket.dto.BasketListResponseDto;
import com.example.meongnyangbook.shop.basket.dto.BasketResponseDto;
import com.example.meongnyangbook.shop.basket.service.BasketService;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mya/baskets")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @PostMapping("/items/{itemNo}")
    public ResponseEntity<ApiResponseDto> createBasket(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long itemNo) {
        ApiResponseDto result = basketService.createBasket(userDetails.getUser(), itemNo);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<BasketListResponseDto> getBaskets(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        BasketListResponseDto result = basketService.getBaskets(userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/items/{itemNo}")
    public ResponseEntity<ApiResponseDto> deleteSingleBasket(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long itemNo) {
        ApiResponseDto result = basketService.deleteSingleBasket(userDetails.getUser(), itemNo);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponseDto> deleteBasket(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto result = basketService.deleteBasket(userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
