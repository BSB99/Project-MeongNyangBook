package com.example.meongnyangbook.shop.item.service;

import com.example.meongnyangbook.backoffice.notification.dto.NoticeRequestDto;
import com.example.meongnyangbook.backoffice.notification.entity.Notice;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.item.dto.ItemRequestDto;
import com.example.meongnyangbook.shop.item.dto.ItemResponseDto;
import com.example.meongnyangbook.shop.item.entity.Item;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {
    ApiResponseDto createItem(ItemRequestDto requestDto);

    List<ItemResponseDto> getItems(Pageable pageable);

    ApiResponseDto updateItem(ItemRequestDto requestDto, Long itemNo);

    ApiResponseDto deleteItem(Long itemNo);

    Item getItem(Long itemNo);
}
