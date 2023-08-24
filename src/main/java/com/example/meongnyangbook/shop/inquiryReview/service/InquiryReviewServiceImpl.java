package com.example.meongnyangbook.shop.inquiryReview.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.inquiryReview.dto.InquiryReviewRequestDto;
import com.example.meongnyangbook.shop.inquiryReview.dto.InquiryReviewResponseDto;
import com.example.meongnyangbook.shop.inquiryReview.entity.InquiryReview;
import com.example.meongnyangbook.shop.inquiryReview.entity.InquiryReviewEnum;
import com.example.meongnyangbook.shop.inquiryReview.repository.InquiryReviewRepository;
import com.example.meongnyangbook.shop.item.entity.Item;
import com.example.meongnyangbook.shop.item.service.ItemService;
import com.example.meongnyangbook.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InquiryReviewServiceImpl implements InquiryReviewService {

  private final InquiryReviewRepository inquiryReviewRepository;
  private final ItemService itemService;


  @Override
  public InquiryReviewResponseDto createInquiryReview(User user, InquiryReviewRequestDto requestDto,
      Long id) {
    Item item = itemService.getItem(id);
    InquiryReview inquiryReview = new InquiryReview(requestDto, user, item);
    inquiryReviewRepository.save(inquiryReview);
    return new InquiryReviewResponseDto(inquiryReview);
  }

  @Override
  public List<InquiryReviewResponseDto> getInquiryReviewList(String categoryName,
      Long id) {
    InquiryReviewEnum categoryEnum = InquiryReviewEnum.fromString(categoryName);

    return inquiryReviewRepository.findByCategory(categoryEnum)
        .stream()
        .map(InquiryReviewResponseDto::new)
        .toList();
  }

  @Override
  public InquiryReviewResponseDto getSingleInquiryReview(Long id) {
    return new InquiryReviewResponseDto(getInquiryReview(id));
  }

  @Override
  @Transactional
  public InquiryReviewResponseDto updateInquiryReview(Long id, User user,
      InquiryReviewRequestDto requestDto) {
    InquiryReview inquiryReview = getInquiryReview(id);
    inquiryReview.setTitle(requestDto.getTitle());
    inquiryReview.setDescription(requestDto.getDescription());
    inquiryReview.setCategory(requestDto.getCategory());

    return new InquiryReviewResponseDto(inquiryReview);
  }

  @Override
  public ApiResponseDto deleteInquiryReview(Long id, User user) {
    InquiryReview inquiryReview = getInquiryReview(id);
    inquiryReviewRepository.delete(inquiryReview);
    return new ApiResponseDto("문의, 리뷰 삭제 완료", 200);
  }

  @Override
  public InquiryReview getInquiryReview(Long id) {
    return inquiryReviewRepository.findById(id).orElseThrow(() -> {
      throw new IllegalArgumentException("문의, 리뷰 글이 없습니다.");
    });
  }
}
