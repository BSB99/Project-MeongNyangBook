package com.example.meongnyangbook.shop.order.entity;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.shop.order.dto.OrderRequestDto;
import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_price")
    private Long totalPrice;

    private String address;

    @Enumerated(value = EnumType.STRING)
    private OrderStatusEnum statusEnum = OrderStatusEnum.ORDER_IN_PROGRESS;

    @Column(name = "recive_name")
    private String reciveName;

    @Column(name = "recive_phone_number")
    private String recivePhoneNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(OrderRequestDto requestDto, User user) {
        this.address = requestDto.getAddress();
        this.reciveName = requestDto.getReciveName();
        this.recivePhoneNumber = requestDto.getRecivePhoneNumber();
        this.user = user;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatusEnum() {
        this.statusEnum = OrderStatusEnum.ORDER_COMPLETED;
    }
}
