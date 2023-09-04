package com.example.meongnyangbook.shop.basket;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "장바구니 API")
@RestController
@RequestMapping("/mya/baskets")
@RequiredArgsConstructor
public class BasketController {

  private final BasketService basketService;

  @Operation(summary = "장바구니에 항목 생성")
  @PostMapping("/items/{itemNo}")
  public ResponseEntity<ApiResponseDto> createBasket(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long itemNo) {
    ApiResponseDto result = basketService.createBasket(userDetails.getUser(), itemNo);

    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @Operation(summary = "장바구니 목록 조회")
  @GetMapping
  public ResponseEntity<BasketListResponseDto> getBaskets(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    BasketListResponseDto result = basketService.getBaskets(userDetails.getUser());

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "장바구니 항목 삭제")
  @DeleteMapping("/items/{itemNo}")
  public ResponseEntity<ApiResponseDto> deleteSingleBasket(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long itemNo) {
    ApiResponseDto result = basketService.deleteSingleBasket(userDetails.getUser(), itemNo);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "장바구니 삭제")
  @DeleteMapping
  public ResponseEntity<ApiResponseDto> deleteBasket(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    ApiResponseDto result = basketService.deleteBasket(userDetails.getUser());

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
