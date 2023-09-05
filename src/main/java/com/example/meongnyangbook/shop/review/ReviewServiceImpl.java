package com.example.meongnyangbook.shop.review;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.item.Item;
import com.example.meongnyangbook.shop.item.ItemService;
import com.example.meongnyangbook.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ItemService itemService;

    @Override
    public ReviewResponseDto createReview(User user, ReviewRequestDto requestDto, Long id) {
        Item item = itemService.getItem(id);
        Review review = new Review(requestDto, user, item);
        reviewRepository.save(review);

        return new ReviewResponseDto(review);
    }

    @Override
    public List<ReviewResponseDto> getReviewList(Long id) {
        return reviewRepository.findAll()
                .stream()
                .map(ReviewResponseDto::new)
                .toList();
    }

    @Override
    public ReviewResponseDto getSingleReview(Long id) {
        return new ReviewResponseDto(getReview(id));
    }

    @Override
    @Transactional
    public ReviewResponseDto updateReview(Long id, User user, ReviewRequestDto requestDto) {
        Review review = getReview(id);
        if (!review.getTitle().equals(requestDto.getTitle())) {
            review.setTitle(requestDto.getTitle());
        }
        if (!review.getDescription().equals(requestDto.getDescription())) {
            review.setDescription(requestDto.getDescription());
        }
        if (!review.getStarRating().equals(requestDto.getStarRating())) {
            review.setStarRating(requestDto.getStarRating());
        }

        return new ReviewResponseDto(review);
    }

    @Override
    public ApiResponseDto deleteReview(Long id, User user) {
        Review review = getReview(id);
        reviewRepository.delete(review);

        return new ApiResponseDto("리뷰 삭제 완료", 200);
    }

    @Override
    public Review getReview(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("리뷰가 없습니다.");
        });
    }
}
