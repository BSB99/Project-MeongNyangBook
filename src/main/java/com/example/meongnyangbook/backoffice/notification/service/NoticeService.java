package com.example.meongnyangbook.backoffice.notification.service;

import com.example.meongnyangbook.backoffice.notification.dto.NoticeRequestDto;
import com.example.meongnyangbook.backoffice.notification.dto.NoticeResponseDto;
import com.example.meongnyangbook.backoffice.notification.entity.Notice;
import com.example.meongnyangbook.common.ApiResponseDto;

import java.util.List;

public interface NoticeService {

    ApiResponseDto createNotice(NoticeRequestDto requestDto);

    ApiResponseDto updateNotice(NoticeRequestDto requestDto, Long noticeNo);

    ApiResponseDto deleteNotice(Long noticeNo);

    List<NoticeResponseDto> getNotices();

    Notice getNotice(Long noticeNo);
}
