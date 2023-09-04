package com.example.meongnyangbook.post.repository.jpa;

import com.example.meongnyangbook.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryQuery {

}
