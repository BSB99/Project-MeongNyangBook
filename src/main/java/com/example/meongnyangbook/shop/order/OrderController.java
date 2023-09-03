package com.example.meongnyangbook.shop.order;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.order.dto.OrderListResponseDto;
import com.example.meongnyangbook.shop.order.dto.OrderRequestDto;
import com.example.meongnyangbook.user.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주문 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/orders")
public class OrderController {

  private final OrderService orderService;

  @Operation(summary = "주문 생성")
  @PostMapping
  public ResponseEntity<ApiResponseDto> createOrder(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody OrderRequestDto requestDto) {
    ApiResponseDto result = orderService.createOrder(userDetails.getUser(), requestDto);

    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @Operation(summary = "주문 상태 수정")
  @PatchMapping
  public ResponseEntity<ApiResponseDto> updateOrder(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    ApiResponseDto result = orderService.updateOrder(userDetails.getUser());

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "주문완료된 항목 가져오기")
  @GetMapping
  public ResponseEntity<OrderListResponseDto> getOrderList(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    OrderListResponseDto result = orderService.getOrderList(userDetails.getUser());

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
