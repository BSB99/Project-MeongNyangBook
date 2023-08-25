package com.example.meongnyangbook.post.attachment.entity;

import com.example.meongnyangbook.post.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "attachment_urls")
public class AttachmentUrl {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 65535)
  private String fileName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;

  public AttachmentUrl(String fileName, Post post) {
    this.fileName = fileName;
    this.post = post;

  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
