package com.example.meongnyangbook.shop.item;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.common.CsvService;
import com.example.meongnyangbook.post.dto.DeleteDto;
import com.example.meongnyangbook.shop.item.dto.ItemListResponseDto;
import com.example.meongnyangbook.shop.item.dto.ItemRequestDto;
import com.example.meongnyangbook.shop.item.dto.ItemResponseDto;
import com.example.meongnyangbook.shop.item.search.ItemSearchListResponseDto;
import com.example.meongnyangbook.user.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "상품 API")
@RestController
@RequestMapping("/mya/items")
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;
  private final CsvService csvService;

  @Operation(summary = "상품 생성")
  @PostMapping
  public ResponseEntity<ApiResponseDto> createItem(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestPart("requestDto") String requestDto,
      @RequestPart("fileName") MultipartFile[] multipartFiles) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    ItemRequestDto itemReqeustDto = objectMapper.readValue(requestDto,
        ItemRequestDto.class);
    ApiResponseDto result = itemService.createItem(itemReqeustDto, multipartFiles);

    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @Operation(summary = "상품 목록 조회")
  @GetMapping
  public ResponseEntity<ItemListResponseDto> getItems(Pageable pageable) {
    ItemListResponseDto result = itemService.getItems(pageable);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "상품 정보 수정")
  @PutMapping(value = "/{itemNo}", consumes = {MediaType.APPLICATION_JSON_VALUE,
      MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<ApiResponseDto> updateItem(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestPart("requestDto") ItemRequestDto requestDto,
      @PathVariable Long itemNo,
      @RequestPart("fileName") MultipartFile[] multipartFiles,
      @RequestPart("deleteFileName") DeleteDto deleteDto) {
    ApiResponseDto result = itemService.updateItem(requestDto, itemNo, multipartFiles,
        deleteDto.getDeleteFileName());

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/{itemNo}")
  public ResponseEntity<ItemResponseDto> getSingleItem(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long itemNo) {
    ItemResponseDto result = itemService.getSingleItem(itemNo);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "상품 삭제")
  @DeleteMapping("/{itemNo}")
  public ResponseEntity<ApiResponseDto> deleteItem(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long itemNo) {
    ApiResponseDto result = itemService.deleteItem(itemNo);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "상품 카테고리 선택")
  @GetMapping("/search")
  public ResponseEntity<ItemListResponseDto> searchItems(Pageable pageable,
      @RequestParam(value = "category", required = false) ItemCategoryEnum category,
      @RequestParam(value = "min", required = false) Long min,
      @RequestParam(value = "max", required = false) Long max) {
    ItemListResponseDto itemListResponseDto = itemService.searchItems(pageable, category, min, max);

    return ResponseEntity.status(HttpStatus.OK).body(itemListResponseDto);
  }

  @Operation(summary = "상품 검색기능 - es")
  @GetMapping("/es")
  public ResponseEntity<ItemSearchListResponseDto> elasticSearchItems(Pageable pageable,
      @RequestParam(value = "keyword", required = false) String keyword,
      @RequestParam(value = "category", required = false) ItemCategoryEnum category,
      @RequestParam(value = "min", required = false) Long min,
      @RequestParam(value = "max", required = false) Long max) {
    ItemSearchListResponseDto result = itemService.elasticSearchItems(pageable, keyword, category,
        min, max);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "csv상품등록 기능")
  @PostMapping("/csv")
  public List<String[]> createCSVItem(@RequestParam String fileName) {
    return csvService.readCsv(fileName);
  }
}
