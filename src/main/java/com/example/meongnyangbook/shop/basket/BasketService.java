package com.example.meongnyangbook.shop.basket;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.User;

public interface BasketService {

  /**
   * 장바구니 만들기
   *
   * @param user
   * @param itemNo
   * @return
   */
  ApiResponseDto createBasket(User user, Long itemNo);

  /**
   * 장바구니 조회
   *
   * @param user
   * @return
   */

  BasketListResponseDto getBaskets(User user);

  /**
   * 장바구니 내 아이템하나 삭제
   *
   * @param user
   * @param itemNo
   * @return
   */

  ApiResponseDto deleteSingleBasket(User user, Long itemNo);

  /**
   * 장바구니 삭제
   *
   * @param user
   * @return
   */

  ApiResponseDto deleteBasket(User user);
}
