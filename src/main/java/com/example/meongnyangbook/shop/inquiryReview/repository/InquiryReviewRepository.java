package com.example.meongnyangbook.shop.inquiryReview.repository;

import com.example.meongnyangbook.shop.inquiryReview.entity.InquiryReview;
import com.example.meongnyangbook.shop.inquiryReview.entity.InquiryReviewEnum;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryReviewRepository extends JpaRepository<InquiryReview, Long> {

  List<InquiryReview> findByCategory(InquiryReviewEnum category);
}
