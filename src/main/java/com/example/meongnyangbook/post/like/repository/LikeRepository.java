package com.example.meongnyangbook.post.like.repository;

import com.example.meongnyangbook.post.like.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<PostLike, Long> {
        Boolean existsByPostIdAndUserId(Long post_id,Long user_id);


        //해당 포스트의 해당 유저가 누른 postLike
        PostLike findByPostIdAndUserId(Long postId,Long user_id);
}
