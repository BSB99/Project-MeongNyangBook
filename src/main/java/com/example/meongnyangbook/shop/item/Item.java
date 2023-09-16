package com.example.meongnyangbook.shop.item;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.shop.attachment.AttachmentItemUrl;
import com.example.meongnyangbook.shop.item.dto.ItemRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
@Setter
@Table(name = "items")
public class Item extends TimeStamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Long price;

  private Long quantity = 10000L;

  private String content;

  @Enumerated(value = EnumType.STRING)
  private ItemCategoryEnum category;

  @OneToOne(mappedBy = "item")
  private AttachmentItemUrl attachmentItemUrl;

  public Item(ItemRequestDto requestDto) {
    this.name = requestDto.getName();
    this.price = requestDto.getPrice();
    this.category = requestDto.getCategory();
    this.content = requestDto.getContent();
  }


}

