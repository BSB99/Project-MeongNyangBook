package com.example.meongnyangbook.shop.review.repository;

import com.example.meongnyangbook.shop.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
