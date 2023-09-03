package com.example.meongnyangbook.post.comment;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "post_comments")
public class Comment extends TimeStamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "content", nullable = false)
  private String content;

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Comment(String content, Post post, User user) {
    this.content = content;
    this.post = post;
    this.user = user;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
