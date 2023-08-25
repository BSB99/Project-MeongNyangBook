package com.example.meongnyangbook.shop.inquiry.inquiryComment.repository;

import com.example.meongnyangbook.shop.inquiry.inquiryComment.entity.InquiryComment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryCommentRepository extends JpaRepository<InquiryComment, Long> {

  Optional<InquiryComment> findByInquiryId(Long inquiryId);
}
