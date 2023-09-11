package com.example.meongnyangbook.shop.inquiry;

import lombok.Getter;

import java.util.List;

@Getter
public class InquiryListResponseDto {
    private Long len;
    private List<InquiryResponseDto> inquiryList;

    public InquiryListResponseDto( List<InquiryResponseDto> inquiryList, Long len) {
        this.len = len;
        this.inquiryList = inquiryList;
    }
}
