package com.example.meongnyangbook.post.adoption.repository;

import com.example.meongnyangbook.post.adoption.entity.Adoption;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {

  Page<Adoption> findAllByOrderByCreatedAtDesc(Pageable pageable);

  List<Adoption> findByUserId(Long userId);
}
