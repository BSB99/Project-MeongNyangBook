package com.example.meongnyangbook.shop.basket.repository;

import com.example.meongnyangbook.shop.basket.entity.Basket;
import com.example.meongnyangbook.shop.order.entity.QOrderItem;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class BasketRepositoryQueryImpl implements BasketRepositoryQuery{
    @PersistenceContext
    private EntityManager em;

    @Override
    public void setBasketToNull(Long basketId) {
        QOrderItem orderItem = QOrderItem.orderItem;

        new JPAUpdateClause(em, orderItem)
                .set(orderItem.basket, (Basket) null)
                .where(orderItem.basket.id.eq(basketId))
                .execute();
    }
}
