package com.example.meongnyangbook.shop.inquiryReview.entity;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.shop.inquiryReview.dto.InquiryReviewRequestDto;
import com.example.meongnyangbook.shop.item.entity.Item;
import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "inquiries_reviews")
public class InquiryReview extends TimeStamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String description;

  @Enumerated(value = EnumType.STRING)
  private InquiryReviewEnum category;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "item_id")
  private Item item;

  public InquiryReview(InquiryReviewRequestDto requestDto, User user, Item item) {
    this.title = requestDto.getTitle();
    this.description = requestDto.getDescription();
    this.category = requestDto.getCategory();
    this.user = user;
    this.item = item;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setCategory(InquiryReviewEnum category) {
    this.category = category;
  }
}
