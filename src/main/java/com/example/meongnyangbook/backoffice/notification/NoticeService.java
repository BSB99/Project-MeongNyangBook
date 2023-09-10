package com.example.meongnyangbook.backoffice.notification;

import com.example.meongnyangbook.common.ApiResponseDto;
import java.util.List;

public interface NoticeService {

  /**
   * 공지사항 생성
   *
   * @param requestDto
   * @return
   */
  ApiResponseDto createNotice(NoticeRequestDto requestDto);

  /**
   * 공지사항 수정
   *
   * @param requestDto
   * @param noticeNo
   * @return
   */
  ApiResponseDto updateNotice(NoticeRequestDto requestDto, Long noticeNo);

  /**
   * 공지사항 삭제
   *
   * @param noticeNo
   * @return
   */
  ApiResponseDto deleteNotice(Long noticeNo);

  /**
   * 공지사항 전체 조회
   *
   * @return
   */
  List<NoticeResponseDto> getNotices();

  Notice getNotice(Long noticeNo);
}
