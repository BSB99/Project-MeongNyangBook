package com.example.meongnyangbook.shop.basket.entity;

import com.example.meongnyangbook.shop.item.entity.Item;
import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "baskets")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cnt = 1;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "itme_id")
    private Item item;

    public Basket(User user, Item item) {
        this.user = user;
        this.item = item;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
