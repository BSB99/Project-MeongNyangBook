package com.example.meongnyangbook.shop.order;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.order.dto.OrderListResponseDto;
import com.example.meongnyangbook.shop.order.dto.OrderRequestDto;
import com.example.meongnyangbook.shop.order.dto.OrderResponseDto;
import com.example.meongnyangbook.user.User;

import java.util.List;

public interface OrderService {

  /**
   * 주문하기
   *
   * @param user
   * @param requestDto
   * @return
   */
  ApiResponseDto createOrder(User user, OrderRequestDto requestDto);

  /**
   * 주문 수정
   *
   * @param user
   * @return
   */
  ApiResponseDto updateOrder(Long orderNo);

  /**
   * 해당유저 주문 리스트 가져오기
   *
   * @param user
   * @return
   */
  OrderListResponseDto getOrderList(User user);

  OrderResponseDto getSingleOrderList(User user, Long orderNo);

  List<OrderResponseDto> getOrderLists();
}
