package com.example.meongnyangbook.post.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<PostLike, Long> {

  Boolean existsByPostIdAndUserId(Long postId, Long userId);

  //해당 포스트의 해당 유저가 누른 postLike
  PostLike findByPostIdAndUserId(Long postId, Long userId);

  Long countByPostId(Long id);
}
