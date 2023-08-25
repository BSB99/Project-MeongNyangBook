package com.example.meongnyangbook.S3.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface S3ItemFileRepository extends JpaRepository<S3ItemFile, Long> {

}
