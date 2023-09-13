package com.example.meongnyangbook.scheduled;

import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.post.repository.PostRepository;
import com.example.meongnyangbook.redis.RedisViewCountUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PostScheduled {
    private final RedisViewCountUtil redisUtil;
    private final PostRepository PostRepository;
    @Scheduled(cron = "0 0 */6 * * *")
    @Transactional
    public void myScheduledMethod() {
        if (!redisUtil.getAllViewedPosts().isEmpty()) {
            for (TypedTuple<String> tuple : redisUtil.getAllViewedPosts()) {
                Long score = Objects.requireNonNull(tuple.getScore()).longValue();
                Long value = Long.parseLong(Objects.requireNonNull(tuple.getValue()));

                Post post = PostRepository.findPostById(value);
                post.setScore(score);
            }
        }
    }
}