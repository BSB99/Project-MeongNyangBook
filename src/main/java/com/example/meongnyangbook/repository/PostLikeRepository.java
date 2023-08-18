package com.example.meongnyangbook.repository;

import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.post.like.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    @Query("SELECT pl.post FROM PostLike pl GROUP BY pl.post ORDER BY COUNT(pl.post) DESC")
    List<Post> findPostsByMostLikes();
}
