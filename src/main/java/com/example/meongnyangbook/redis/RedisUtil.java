package com.example.meongnyangbook.redis;

import com.example.meongnyangbook.user.jwt.JwtUtil;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUtil {

  private final RedisTemplate<String, Object> redisTemplate;
  private final RedisTemplate<String, Object> redisBlackListTemplate;
  private final RedisTemplate<String, String> redis;


  public void set(String key, Object o, int minutes) {
    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
    redisTemplate.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
  }

  public Object get(String key) {
    return redisTemplate.opsForValue().get(key);
  }


  public String getRefreshToken(String username) {
    return redisTemplate.opsForValue().get(username).toString();
  }

  public void saveRefreshToken(String username, String refreshToken) {
    // ExpirationTime 설정을 통해 자동 삭제 처리
    redisTemplate.opsForValue()
        .set(username, refreshToken, JwtUtil.REFRESH_TOKEN_TIME, TimeUnit.MILLISECONDS);
  }

  public boolean delete(String key) {
    return Boolean.TRUE.equals(redisTemplate.delete(key));
  }

  public boolean hasKey(String key) {
    return Boolean.TRUE.equals(redisTemplate.hasKey(key));
  }

  public void setBlackList(String key, Long minutes) {
    redisBlackListTemplate.opsForValue().set(key, "logout", minutes, TimeUnit.MINUTES);
  }

  public boolean deleteBlackList(String key) {
    return Boolean.TRUE.equals(redisBlackListTemplate.delete(key));
  }

  public boolean hasKeyBlackList(String key) {
    return Boolean.TRUE.equals(redisBlackListTemplate.hasKey(key));
  }

  public Object getBlackList(String key) {
    return redisBlackListTemplate.opsForValue().get(key);
  }

  // 조회수 증가 가능 여부 확인
  public boolean checkAndIncrementViewCount(String postId, String userId) {
    String userKey = "view_count_number:" + postId + ":" + userId;
    Long addedCount = redis.opsForSet().add(userKey, userId);

    if (addedCount == 1L) {
      redis.expire(userKey, 3600, TimeUnit.SECONDS);
      return true; // 조회 가능한 경우 조회수 증가
    }
    return false; // 이미 조회한 경우 조회수 증가하지 않음
  }

  // post 조회수 확인
  public Double getViewCount(String postId) {
    return redis.opsForZSet().score("view_count:", postId);
  }

  // post 조회수 증가 로직
  public void incrementViewCount(String postId) {
    redis.opsForZSet().incrementScore("view_count:", postId, 1);
  }


  public Set<String> getTopViewedPosts(int count) {
    return redis.opsForZSet().reverseRange("view_count:", 0, count - 1);
  }

}