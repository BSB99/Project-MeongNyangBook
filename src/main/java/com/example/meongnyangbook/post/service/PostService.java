package com.example.meongnyangbook.post.service;

import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.post.like.entity.PostLike;
import com.example.meongnyangbook.post.like.repository.PostLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.meongnyangbook.post.repository.PostRepository;
import com.example.meongnyangbook.user.entity.User;

import java.util.List;

@Service
public class PostService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostLikeRepository postLikeRepository, PostRepository postRepository) {
        this.postLikeRepository = postLikeRepository;
        this.postRepository = postRepository;
    }

    public boolean hasLiked(User user, Post post) {
        return postLikeRepository.existsByUserAndPost(user, post);
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public void toggleLike(User user, Post post) {
        boolean hasLiked = postLikeRepository.existsByUserAndPost(user, post);
        if (hasLiked) {
            postLikeRepository.deleteByUserAndPost(user, post);
        } else {
            PostLike postLike = new PostLike(user, post);
            postLikeRepository.save(postLike);
        }
    }

    public List<Post> getPostsByMostLikes() {
        List<Post> bestPosts = postLikeRepository.findPostsByMostLikes();

        // bestPosts에 대해 현재 사용자의 좋아요 여부를 판단하는 로직 추가

        return bestPosts;
    }
}

