package com.example.meongnyangbook.shop.order;

import com.example.meongnyangbook.shop.basket.Basket;
import com.example.meongnyangbook.shop.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "order_item_histories")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_cnt")
    private Integer itemCnt;

    @Column(name = "item_price")
    private Long itemPrice;

    @Column(name = "total_price")
    private Long totalPrice;

    @ManyToOne
    @JoinColumn(name = "itme_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "basket_id")
    private Basket basket;

    public OrderItem(Integer itemCnt, Item item, Order order, Basket basket) {
        this.itemCnt = itemCnt;
        this.item = item;
        this.order = order;
        this.basket = basket;
        this.itemPrice = item.getPrice();
        this.totalPrice = item.getPrice() * itemCnt;
    }
}
