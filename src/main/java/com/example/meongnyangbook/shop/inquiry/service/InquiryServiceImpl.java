package com.example.meongnyangbook.shop.inquiry.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.inquiry.dto.InquiryRequestDto;
import com.example.meongnyangbook.shop.inquiry.dto.InquiryResponseDto;
import com.example.meongnyangbook.shop.inquiry.entity.Inquiry;
import com.example.meongnyangbook.shop.inquiry.repository.InquiryRepository;
import com.example.meongnyangbook.shop.item.entity.Item;
import com.example.meongnyangbook.shop.item.service.ItemService;
import com.example.meongnyangbook.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;
    private final ItemService itemService;

    @Override
    public InquiryResponseDto createInquiry(User user, InquiryRequestDto requestDto, Long id) {
        Item item = itemService.getItem(id);
        Inquiry inquiry = new Inquiry(requestDto, user, item);
        inquiryRepository.save(inquiry);
        return new InquiryResponseDto(inquiry);
    }

    @Override
    public List<InquiryResponseDto> getInquiryList(Long id) {
        return inquiryRepository.findAll()
                .stream()
                .map(InquiryResponseDto::new)
                .toList();
    }

    @Override
    public InquiryResponseDto getSingleInquiry(Long id) {
        return new InquiryResponseDto(getInquiry(id));
    }

    @Override
    @Transactional
    public InquiryResponseDto updateInquiry(Long id, User user, InquiryRequestDto requestDto) {
        Inquiry inquiry = getInquiry(id);
        inquiry.setTitle(requestDto.getTitle());
        inquiry.setDescription(requestDto.getDescription());

        return new InquiryResponseDto(inquiry);
    }

    @Override
    public ApiResponseDto deleteInquiry(Long id, User user) {
        Inquiry inquiry = getInquiry(id);
        inquiryRepository.delete(inquiry);
        return new ApiResponseDto("문의 삭제 완료", 200);
    }

    @Override
    public Inquiry getInquiry(Long id) {
        return inquiryRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("문의글이 없습니다.");
        });
    }
}
