package com.example.meongnyangbook.post.repository.jpa;

import com.example.meongnyangbook.post.entity.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositoryQuery {

  Post findPostById(Long postId);
}
