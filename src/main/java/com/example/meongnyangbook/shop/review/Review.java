package com.example.meongnyangbook.shop.review;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.shop.item.Item;
import com.example.meongnyangbook.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
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
