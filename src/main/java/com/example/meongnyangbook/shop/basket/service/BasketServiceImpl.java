package com.example.meongnyangbook.shop.basket.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.basket.dto.BasketListResponseDto;
import com.example.meongnyangbook.shop.basket.dto.BasketResponseDto;
import com.example.meongnyangbook.shop.basket.entity.Basket;
import com.example.meongnyangbook.shop.basket.repository.BasketRepository;
import com.example.meongnyangbook.shop.item.entity.Item;
import com.example.meongnyangbook.shop.item.service.ItemService;
import com.example.meongnyangbook.shop.item.service.ItemServiceImpl;
import com.example.meongnyangbook.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService{
    private final BasketRepository basketRepository;
    private final ItemService itemService;

    @Override
    @Transactional
    public ApiResponseDto createBasket(User user, Long itemNo) {
        Item item = itemService.getItem(itemNo);

        Basket existingBasket = basketRepository.findByUserAndItem(user, item);

        if (existingBasket != null) {
            existingBasket.setCnt(existingBasket.getCnt() + 1);
        } else {
            Basket basket = new Basket(user, item);
            basketRepository.save(basket);
        }

        return new ApiResponseDto("장바구니에 담았습니다", 201);
    };

    @Override
    public BasketListResponseDto getBaskets(User user) {
        List<BasketResponseDto> basketResponseDto = basketRepository.findByUser(user).stream().map(BasketResponseDto::new).toList();
        return new BasketListResponseDto(basketResponseDto);
    };

    @Override
    @Transactional
    public ApiResponseDto deleteSingleBasket(User user, Long itemNo) {
        Item item = itemService.getItem(itemNo);

        Basket basket = basketRepository.findByUserAndItem(user, item);

        if(!basket.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("자신의 장바구니 항목이 아닙니다.");
        }

        if(basket.getCnt() > 1) {
          basket.setCnt(basket.getCnt() - 1);
        } else {
            basketRepository.delete(basket);
        }

        return new ApiResponseDto("원하는 항목이 제거되었습니다.", 200);
    };

    @Override
    public ApiResponseDto deleteBasket(User user) {
        List<Basket> basketList = basketRepository.findByUser(user);

        basketRepository.deleteAll(basketList);

        return new ApiResponseDto("장바구니가 삭제 되었습니다", 200);
    };
}
