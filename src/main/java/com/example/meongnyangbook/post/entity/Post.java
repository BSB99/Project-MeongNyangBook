package com.example.meongnyangbook.post.entity;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.post.attachment.entity.AttachmentUrl;
import com.example.meongnyangbook.post.comment.entity.Comment;
import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.*;

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

  @ManyToOne
  @JoinColumn(name = "user_id")
  protected User user;

  @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
  private List<Comment> commentList = new ArrayList<>();

  @OneToOne(mappedBy = "post")
  private AttachmentUrl attachmentUrl;
}
