package com.example.meongnyangbook.shop.item;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.shop.attachment.AttachmentItemUrl;
import com.example.meongnyangbook.shop.item.dto.ItemRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setCategory(ItemCategoryEnum category) {
        this.category = category;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
