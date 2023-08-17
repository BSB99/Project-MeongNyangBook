package com.example.meongnyangbook.service;

import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.repository.PostLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostLikeRepository postLikeRepository;

    @Autowired
    public PostService(PostLikeRepository postLikeRepository) {
        this.postLikeRepository = postLikeRepository;
    }

    public List<Post> getPostsByMostLikes() {
        return postLikeRepository.findPostsByMostLikes();
    }
}
