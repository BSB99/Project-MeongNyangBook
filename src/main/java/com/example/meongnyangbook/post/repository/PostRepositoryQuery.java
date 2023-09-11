package com.example.meongnyangbook.post.repository;

import com.example.meongnyangbook.post.entity.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositoryQuery {

    Post findPostById(Long postId);
}
