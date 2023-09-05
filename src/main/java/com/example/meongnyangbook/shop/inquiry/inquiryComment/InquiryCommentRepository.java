package com.example.meongnyangbook.shop.inquiry.inquiryComment;

import com.example.meongnyangbook.shop.inquiry.inquiryComment.InquiryComment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryCommentRepository extends JpaRepository<InquiryComment, Long> {

  Optional<InquiryComment> findByInquiryId(Long inquiryId);
}
