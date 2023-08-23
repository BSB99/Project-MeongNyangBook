package com.example.meongnyangbook.backoffice.notification.service;

import com.example.meongnyangbook.backoffice.notification.dto.NoticeRequestDto;
import com.example.meongnyangbook.backoffice.notification.dto.NoticeResponseDto;
import com.example.meongnyangbook.backoffice.notification.entity.Notice;
import com.example.meongnyangbook.backoffice.notification.repository.NoticeRepository;
import com.example.meongnyangbook.common.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{
    private final NoticeRepository noticeRepository;

    @Override
    public ApiResponseDto createNotice(NoticeRequestDto requestDto) {
        Notice notice = new Notice(requestDto);

        noticeRepository.save(notice);

        return new ApiResponseDto("공지사항 작성", 201);
    }

    @Override
    public List<NoticeResponseDto> getNotices() {
        return noticeRepository.findAll().stream().map(NoticeResponseDto::new).toList();
    };

    @Override
    @Transactional
    public  ApiResponseDto updateNotice(NoticeRequestDto requestDto, Long noticeNo) {
        Notice notice = getNotice(noticeNo);

        notice.setDescription(requestDto.getDescription());

        return new ApiResponseDto("공지사항 수정", 200);
    };

    @Override
    public ApiResponseDto deleteNotice(Long noticeNo) {
        Notice notice = getNotice(noticeNo);

        noticeRepository.delete(notice);

        return new ApiResponseDto("공지사항 삭제", 200);
    };

    @Override
    public Notice getNotice(Long noticeNo) {
        return noticeRepository.findById(noticeNo).orElseThrow(() -> {
            throw new IllegalArgumentException("공지사항 존재 X");
        });
    }
}
