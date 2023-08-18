package com.example.meongnyangbook.post.repository;

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
    public List<Post> getPostFindByTitleList(String keyword, Pageable pageable) {
        QPost post = QPost.post;

        OrderSpecifier<?> orderSpecifier = post.createdAt.desc();

        return jpaQueryFactory.selectFrom(post)
                .where(post.title.like("%" + keyword + "%"))
                .orderBy(orderSpecifier) // 정렬 조건 추가
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
