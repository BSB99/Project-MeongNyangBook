package com.example.meongnyangbook.shop.review.entity;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.shop.item.entity.Item;
import com.example.meongnyangbook.shop.review.dto.ReviewRequestDto;
import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reviews")
public class Review extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Min(1)
    @Max(5)
    @Column(name = "star_rating")
    private Integer starRating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public Review(ReviewRequestDto requestDto, User user, Item item) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
        this.starRating = requestDto.getStarRating();
        this.user = user;
        this.item = item;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStarRating(Integer starRating) {
        this.starRating = starRating;
    }
}
