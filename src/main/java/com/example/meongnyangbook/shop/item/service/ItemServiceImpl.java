package com.example.meongnyangbook.shop.item.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.item.dto.ItemRequestDto;
import com.example.meongnyangbook.shop.item.dto.ItemResponseDto;
import com.example.meongnyangbook.shop.item.entity.Item;
import com.example.meongnyangbook.shop.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;

    @Override
    public ApiResponseDto createItem(ItemRequestDto requestDto) {
        Item item = new Item(requestDto);

        itemRepository.save(item);

        return new ApiResponseDto("물품 등록 완료", 201);
    };

    @Override
    public List<ItemResponseDto> getItems(Pageable pageable) {
        return itemRepository.findAllByOrderByCreatedAtDesc(pageable).stream().map(ItemResponseDto::new).toList();
    };

    @Override
    @Transactional
    public ApiResponseDto updateItem(ItemRequestDto requestDto, Long itemNo) {
        Item item = getItem(itemNo);

        if (!item.getName().equals(requestDto.getName())) {
            item.setName(requestDto.getName());
        }

        if (!item.getCategory().equals(requestDto.getCategory())) {
            item.setCategory(requestDto.getCategory());
        }

        if (!item.getPrice().equals(requestDto.getPrice())) {
            item.setPrice(requestDto.getPrice());
        }

        return new ApiResponseDto("물품 수정 완료", 200);
    };

    @Override
    public ApiResponseDto deleteItem(Long itemNo) {
        Item item = getItem(itemNo);

        itemRepository.delete(item);

        return new ApiResponseDto("물품 삭제 완료", 200);
    };

    @Override
    public Item getItem(Long itemNo) {
        return itemRepository.findById(itemNo).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않는 물품입니다.");
        });
    }
}
