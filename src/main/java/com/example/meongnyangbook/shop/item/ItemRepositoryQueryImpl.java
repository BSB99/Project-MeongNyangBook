package com.example.meongnyangbook.shop.item;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class ItemRepositoryQueryImpl implements ItemRepositoryQuery{
    private final JPAQueryFactory jpaQueryFactory;
    private QItem item = QItem.item;

    @Override
    public List<Item> searchItemList(Long min, Long max, Pageable pageable, ItemCategoryEnum category) {
        return jpaQueryFactory.selectFrom(item)
                .where(priceGoe(min), priceLoe(max), categoryEq(category))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public int searchItemListCnt(Long min, Long max, ItemCategoryEnum category) {
        return jpaQueryFactory.selectFrom(item)
                .where(priceGoe(min), priceLoe(max), categoryEq(category))
                .fetch().size();
    }

    private BooleanExpression priceGoe(Long min) {
        if (min == null) {
            return null;
        }
        return QItem.item.price.goe(min);
    }

    private BooleanExpression priceLoe(Long max) {
        if (max == null) {
            return null;
        }
        return QItem.item.price.loe(max);
    }

    private BooleanExpression categoryEq(ItemCategoryEnum category) {
        if (category == null) {
            return null;
        }
        return QItem.item.category.eq(category);
    }
}






