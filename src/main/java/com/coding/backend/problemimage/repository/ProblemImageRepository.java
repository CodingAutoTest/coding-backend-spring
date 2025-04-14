package com.coding.backend.problemimage.repository;

import com.coding.backend.problemimage.entity.ProblemImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemImageRepository extends JpaRepository<ProblemImage, Integer> {
}
