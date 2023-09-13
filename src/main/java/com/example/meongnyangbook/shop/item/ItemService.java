package com.example.meongnyangbook.shop.item;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.item.dto.ItemListResponseDto;
import com.example.meongnyangbook.shop.item.dto.ItemRequestDto;
import com.example.meongnyangbook.shop.item.dto.ItemResponseDto;
import com.example.meongnyangbook.shop.item.search.ItemSearchListResponseDto;
import com.example.meongnyangbook.shop.item.search.ItemSearchResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ItemService {

  /**
   * 아이템 만들기
   *
   * @param requestDto
   * @param multipartFiles
   * @return
   */
  ApiResponseDto createItem(ItemRequestDto requestDto, MultipartFile[] multipartFiles);

  /**
   * 아이템 페이지 조회
   *
   * @param pageable
   * @return
   */
  ItemListResponseDto getItems(Pageable pageable);

  /**
   * 아이템 수정
   *
   * @param requestDto
   * @param itemNo
   * @param multipartFiles
   * @return
   */
  ApiResponseDto updateItem(ItemRequestDto requestDto, Long itemNo, MultipartFile[] multipartFiles,
      String[] deleteDto);

  /**
   * 아이템 삭제
   *
   * @param itemNo
   * @return
   */

  ApiResponseDto deleteItem(Long itemNo);

  /**
   * 단일 아이템 조회
   *
   * @param itemNo
   * @return
   */
  ItemResponseDto getSingleItem(Long itemNo);

  List<ItemSearchResponseDto> searchItem(String keyword);

  /**
   * 아이템 정보 찾아서 가져오기
   *
   * @param itemNo
   * @return
   */
  Item getItem(Long itemNo);

  ItemListResponseDto searchItems(Pageable pageable, ItemCategoryEnum category, Long min, Long max);

  ItemSearchListResponseDto elasticSearchItems(Pageable pageable, String keyword,
      ItemCategoryEnum category, Long min,
      Long max);
}
