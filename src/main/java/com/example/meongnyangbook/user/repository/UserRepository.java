package com.example.meongnyangbook.user.repository;

import com.example.meongnyangbook.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  Optional<User> findByNickname(String profilename);

  Optional<User> findByKakaoId(Long kakaoId);
}
