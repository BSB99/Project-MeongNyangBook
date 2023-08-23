package com.example.meongnyangbook.post.entity;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.post.comment.entity.Comment;
import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // 변경해서 바꿀예정
public abstract class Post extends TimeStamped {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id;

  @Column(name = "title", nullable = false)
  protected String title;

  @Column(name = "description", nullable = false)
  protected String description;

  @Column(name = "view_count")
  protected Long viewCount = 0L;

  @ManyToOne
  @JoinColumn(name = "user_id")
  protected User user;

  @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
  private List<Comment> commentList = new ArrayList<>();

  public void setViewCount(Long viewCount) {
    this.viewCount = viewCount;
  }
}
