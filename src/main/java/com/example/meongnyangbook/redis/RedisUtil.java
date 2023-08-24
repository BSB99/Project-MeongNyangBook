package com.example.meongnyangbook.redis;

import com.example.meongnyangbook.user.jwt.JwtUtil;
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


}