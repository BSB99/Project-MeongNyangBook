package com.example.meongnyangbook.post.repository;

import com.example.meongnyangbook.post.adoption.entity.QAdoption;
import com.example.meongnyangbook.post.community.entity.QCommunity;
import com.example.meongnyangbook.post.entity.CategoryEnum;
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.post.entity.QPost;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryQueryImpl implements PostRepositoryQuery{

    private final JPAQueryFactory jpaQueryFactory;


    // 페이징 정렬, 키워드 검색 (title)
    // part: community, adoption -> RequestParam
    // controller -> adoptionController / communityController
    // queryDsl 다시 짜기

    @Override
    public Post findPostById(Long postId) {
        QAdoption qAdoption = QAdoption.adoption;
        QCommunity qCommunity = QCommunity.community;

        Post adoptionPost = jpaQueryFactory.selectFrom(qAdoption)
                .where(qAdoption.id.eq(postId))
                .fetchOne();

        if(adoptionPost != null) {
            return adoptionPost;
        }

        return jpaQueryFactory.selectFrom(qCommunity)
                .where(qCommunity.id.eq(postId))
                .fetchOne();
    }
}
