package com.example.meongnyangbook.shop.item.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.item.dto.ItemListResponseDto;
import com.example.meongnyangbook.shop.item.dto.ItemRequestDto;
import com.example.meongnyangbook.shop.item.dto.ItemResponseDto;
import com.example.meongnyangbook.shop.item.entity.Item;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ItemService {

  ApiResponseDto createItem(ItemRequestDto requestDto, MultipartFile[] multipartFiles);

  ItemListResponseDto getItems(Pageable pageable);

  ApiResponseDto updateItem(ItemRequestDto requestDto, Long itemNo, MultipartFile[] multipartFiles,
      String[] deleteDto);

  ApiResponseDto deleteItem(Long itemNo);

  Item getItem(Long itemNo);
}
