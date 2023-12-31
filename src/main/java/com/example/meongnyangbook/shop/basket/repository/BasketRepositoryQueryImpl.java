package com.example.meongnyangbook.shop.basket.repository;

import com.example.meongnyangbook.shop.basket.Basket;
import com.example.meongnyangbook.shop.order.QOrderItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BasketRepositoryQueryImpl implements BasketRepositoryQuery {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void setBasketToNull(Long basketId) {
        QOrderItem orderItem = QOrderItem.orderItem;

        jpaQueryFactory.update(orderItem)
                .set(orderItem.basket, (Basket) null)
                .where(orderItem.basket.id.eq(basketId))
                .execute();
    }
}
