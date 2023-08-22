package com.example.meongnyangbook.post.adoption.repository;

import com.example.meongnyangbook.post.adoption.entity.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
    List<Adoption> findByUserId(Long userId);
}
