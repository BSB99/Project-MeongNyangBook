package com.example.meongnyangbook.post.entity;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.post.attachment.AttachmentUrl;
import com.example.meongnyangbook.post.comment.Comment;
import com.example.meongnyangbook.post.like.PostLike;
import com.example.meongnyangbook.user.User;
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
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // 변경해서 바꿀예정
public abstract class Post extends TimeStamped {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id;

  @Column(name = "title", nullable = false)
  protected String title;

  @Column(name = "description", nullable = false)
  protected String description;

  @Column
  protected Long score = 0L;

  @ManyToOne
  @JoinColumn(name = "user_id")
  protected User user;

  @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
  private List<Comment> commentList = new ArrayList<>();

  @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
  private List<PostLike> postLikes = new ArrayList<>();

  @OneToOne(mappedBy = "post")
  private AttachmentUrl attachmentUrl;

  public void setScore(Long score) {
    this.score = score;
  }
}
