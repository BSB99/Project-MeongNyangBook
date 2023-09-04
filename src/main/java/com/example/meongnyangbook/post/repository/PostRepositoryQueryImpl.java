package com.example.meongnyangbook.post.repository;

import com.example.meongnyangbook.post.adoptionPost.entity.QAdoption;
import com.example.meongnyangbook.post.communityPost.entity.QCommunity;
import com.example.meongnyangbook.post.entity.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryQueryImpl implements PostRepositoryQuery {

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

    if (adoptionPost != null) {
      return adoptionPost;
    }

    return jpaQueryFactory.selectFrom(qCommunity)
        .where(qCommunity.id.eq(postId))
        .fetchOne();
  }
}
