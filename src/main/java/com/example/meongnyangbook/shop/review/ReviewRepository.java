package com.example.meongnyangbook.shop.review;

import com.example.meongnyangbook.shop.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByItemId(Long itemId);
}
