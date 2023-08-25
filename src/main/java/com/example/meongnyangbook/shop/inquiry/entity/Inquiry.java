package com.example.meongnyangbook.shop.inquiry.entity;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.shop.inquiry.dto.InquiryRequestDto;
import com.example.meongnyangbook.shop.inquiry.inquiryComment.entity.InquiryComment;
import com.example.meongnyangbook.shop.item.entity.Item;
import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "inquiries")
public class Inquiry extends TimeStamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String description;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "item_id")
  private Item item;

  @OneToOne(mappedBy = "inquiry")
  private InquiryComment inquiryComment;

  public Inquiry(InquiryRequestDto requestDto, User user, Item item) {
    this.title = requestDto.getTitle();
    this.description = requestDto.getDescription();
    this.user = user;
    this.item = item;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}