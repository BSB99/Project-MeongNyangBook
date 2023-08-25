package com.example.meongnyangbook.shop.attachment;

import com.example.meongnyangbook.shop.item.entity.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "attachment_item_url")
public class AttachmentItemUrl {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 65535)
  private String fileName;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;

  public AttachmentItemUrl(String fileName, Item item) {
    this.fileName = fileName;
    this.item = item;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
