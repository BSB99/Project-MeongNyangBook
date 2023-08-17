package com.example.meongnyangbook.post.service;

import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.post.repository.PostLikeRepository;
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
        List<Post> bestPosts = postLikeRepository.findPostsByMostLikes();

        // bestPosts에 대해 현재 사용자의 좋아요 여부를 판단하는 로직 추가

        return bestPosts;
    }
}

