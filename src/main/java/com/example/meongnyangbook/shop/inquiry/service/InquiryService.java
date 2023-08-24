package com.example.meongnyangbook.shop.inquiry.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.inquiry.dto.InquiryRequestDto;
import com.example.meongnyangbook.shop.inquiry.dto.InquiryResponseDto;
import com.example.meongnyangbook.shop.inquiry.entity.Inquiry;
import com.example.meongnyangbook.user.entity.User;

import java.util.List;


public interface InquiryService {

    InquiryResponseDto createInquiry(User user, InquiryRequestDto requestDto,
                                     Long id);

    List<InquiryResponseDto> getInquiryList(Long id);

    InquiryResponseDto getSingleInquiry(Long id);

    InquiryResponseDto updateInquiry(Long id, User user,
                                     InquiryRequestDto requestDto);

    ApiResponseDto deleteInquiry(Long id, User user);

    Inquiry getInquiry(Long id);

}
