package com.example.meongnyangbook.shop.inquiry.inquiryComment.entity;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.shop.inquiry.entity.Inquiry;
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
@Table(name = "inquiry_comments")
public class InquiryComment extends TimeStamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String contents;

  @OneToOne
  @JoinColumn(name = "inquiry_id")
  private Inquiry inquiry;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public InquiryComment(String contents, Inquiry inquiry, User user) {
    this.contents = contents;
    this.inquiry = inquiry;
    this.user = user;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }
}
