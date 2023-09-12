package com.example.meongnyangbook.redis;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisViewCountUtil {

  private final RedisTemplate<String, String> redis;

  // 조회수 증가 가능 여부 확인
  public boolean checkAndIncrementViewCount(String postId, String userId) {
    String userKey = "view_count_number:" + postId + ":" + userId;
    Long addedCount = redis.opsForSet().add(userKey, userId);

    if (addedCount == 1L) {
      redis.expire(userKey, 5, TimeUnit.SECONDS);
      return true; // 조회 가능한 경우 조회수 증가
    }
    return false; // 이미 조회한 경우 조회수 증가하지 않음
  }

  // post 조회수 확인 *
  public Double getViewPostCount(String postId) {
    return redis.opsForZSet().score("view_count_post:", postId);
  }

  // post 조회수 증가 로직 *
  public void incrementPostViewCount(String postId) {
    redis.opsForZSet().incrementScore("view_count_post:", postId, 1);
  }

  public Set<ZSetOperations.TypedTuple<String>> getAllViewedPosts() {
    return redis.opsForZSet().reverseRangeWithScores("view_count_post:", 0, -1);
  }

}
