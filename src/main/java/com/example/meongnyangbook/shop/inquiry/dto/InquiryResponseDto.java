package com.example.meongnyangbook.shop.inquiry.dto;

import com.example.meongnyangbook.shop.inquiry.entity.Inquiry;
import lombok.Getter;

@Getter
public class InquiryResponseDto {

    private Long inquiryId;
    private String title;
    private String description;
    private Long userId;
    private Long itemId;

    public InquiryResponseDto(Inquiry inquiry) {
        this.inquiryId = inquiry.getId();
        this.title = inquiry.getTitle();
        this.description = inquiry.getDescription();
        this.userId = inquiry.getUser().getId();
        this.itemId = inquiry.getItem().getId();
    }
}
