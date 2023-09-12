package com.example.meongnyangbook.shop.item;

import com.example.meongnyangbook.S3.S3Service;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.attachment.AttachmentItemUrl;
import com.example.meongnyangbook.shop.attachment.AttachmentItemUrlRepository;
import com.example.meongnyangbook.shop.item.dto.ItemListResponseDto;
import com.example.meongnyangbook.shop.item.dto.ItemRequestDto;
import com.example.meongnyangbook.shop.item.dto.ItemResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

  private final ItemRepository itemRepository;
  private final S3Service s3Service;
  private final AttachmentItemUrlRepository attachmentItemUrlRepository;


  @Override
  public ApiResponseDto createItem(ItemRequestDto requestDto, MultipartFile[] multipartFiles) {
    Item item = new Item(requestDto);

    itemRepository.save(item);
    List<String> filePaths = s3Service.uploadFiles(multipartFiles);

    String fileUrls = "";
    for (String fileUrl : filePaths) {
      fileUrls = fileUrls + "," + fileUrl;
    }
    String fileUrlResult = fileUrls.replaceFirst("^,", "");
    AttachmentItemUrl file = new AttachmentItemUrl(fileUrlResult, item);

    attachmentItemUrlRepository.save(file);

    return new ApiResponseDto("물품 등록 완료", 201);
  }

  @Override
  public ItemListResponseDto getItems(Pageable pageable) {
    List<ItemResponseDto> itemResponseDto = itemRepository.findAllByOrderByCreatedAtDesc(pageable)
        .stream().map(ItemResponseDto::new)
        .toList();
    int itemLen = itemRepository.findAll().size();

    return new ItemListResponseDto(itemResponseDto, itemLen);
  }

  @Override
  @Transactional
  public ApiResponseDto updateItem(ItemRequestDto requestDto, Long itemNo,
      MultipartFile[] multipartFiles, String[] deleteFileNames) {
    Item item = getItem(itemNo);

    AttachmentItemUrl attachmentItemUrl = attachmentItemUrlRepository.findByItemId(itemNo);

    String[] filenames = attachmentItemUrl.getFileName().split(",");
    String deleteAfterFileNames = "";
    int sizeCheck = 0;
    for (String filename : filenames) {
      for (String deleteFileName : deleteFileNames) {
        if (!filename.contains(deleteFileName)) {
          deleteAfterFileNames = deleteAfterFileNames + "," + filename;
          sizeCheck++;
        } else {
          s3Service.deleteFile(filename);
        }
      }
    }
    List<String> uploadFileNames = s3Service.uploadFiles(multipartFiles);
    if ((uploadFileNames.size() + sizeCheck) > 5) {
      throw new IllegalArgumentException("사진의 최대 개수는 5개입니다.");
    }
    String combineUploadFileName = s3Service.CombineString(uploadFileNames);

    String replaceDeleteAfterFileName = deleteAfterFileNames.replaceFirst("^,", "");
    String replaceUploadFileName = combineUploadFileName.replaceFirst("^,", "");
    String result = (replaceDeleteAfterFileName + "," + replaceUploadFileName).replaceFirst("^,",
        "");
    attachmentItemUrl.setFileName(result);

    item.setName(requestDto.getName());
    item.setCategory(requestDto.getCategory());
    item.setPrice(requestDto.getPrice());

    return new ApiResponseDto("물품 수정 완료", 200);
  }

  @Override
  @Transactional
  public ApiResponseDto deleteItem(Long itemNo) {
    Item item = getItem(itemNo);

    AttachmentItemUrl attachmentItemUrl = attachmentItemUrlRepository.findByItemId(itemNo);
    String[] fileNames = attachmentItemUrl.getFileName().split(",");
    for (String fileName : fileNames) {
      s3Service.deleteFile(fileName);
    }
    attachmentItemUrlRepository.deleteByItemId(itemNo);
    itemRepository.delete(item);

    return new ApiResponseDto("물품 삭제 완료", 200);
  }

  @Override
  public ItemResponseDto getSingleItem(Long itemNo) {
    Item item = getItem(itemNo);

    return new ItemResponseDto(item);
  }
  @Override
  public Item getItem(Long itemNo) {
    return itemRepository.findById(itemNo).orElseThrow(() -> {
      throw new IllegalArgumentException("존재하지 않는 물품입니다.");
    });
  }

  @Override
  public ItemListResponseDto searchItems(Pageable pageable, ItemCategoryEnum category, Long min, Long max) {
    List<ItemResponseDto> itemResponseDto = itemRepository.searchItemList(min, max, pageable, category)
              .stream().map(ItemResponseDto::new)
              .toList();

    int itemLen = itemRepository.searchItemListCnt(min, max, category);

    return new ItemListResponseDto(itemResponseDto, itemLen);
  }
}
