package com.example.meongnyangbook.shop.inquiry.inquiryComment;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.inquiry.Inquiry;
import com.example.meongnyangbook.shop.inquiry.InquiryService;
import com.example.meongnyangbook.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InquiryCommentServiceImpl implements InquiryCommentService {

  private final InquiryCommentRepository inquiryCommentRepository;
  private final InquiryService inquiryService;

  @Override
  public InquiryCommentResponseDto createInquiryComment(Long inquiryId, User user,
      InquiryCommentRequestDto requestDto) {
    Inquiry inquiry = inquiryService.getInquiry(inquiryId);
    if (inquiryCommentRepository.findByInquiryId(inquiryId).isPresent()) {
      throw new IllegalArgumentException("답글이 존재합니다.");
    }

    InquiryComment inquiryComment = new InquiryComment(requestDto.getContents(), inquiry, user);
    inquiryCommentRepository.save(inquiryComment);

    return new InquiryCommentResponseDto(inquiryComment);
  }

  @Override
  @Transactional
  public InquiryCommentResponseDto updateInquiryComment(Long inquiryId, User user,
      InquiryCommentRequestDto requestDto) {
    InquiryComment inquiryComment = getInquiryComment(inquiryId);
    inquiryComment.setContents(requestDto.getContents());
    return new InquiryCommentResponseDto(inquiryComment);
  }

  @Override
  public ApiResponseDto deleteInquiryComment(Long inquiryId, User user) {
    inquiryCommentRepository.delete(getInquiryComment(inquiryId));
    return new ApiResponseDto("답글이 삭제 되었습니다.", 200);
  }

  @Override
  public InquiryComment getInquiryComment(Long id) {
    return inquiryCommentRepository.findById(id).orElseThrow(() -> {
      throw new IllegalArgumentException("답글이 존재하지 않습니다.");
    });
  }
}
