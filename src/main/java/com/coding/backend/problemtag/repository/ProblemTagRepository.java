package com.coding.backend.problemtag.repository;

import com.coding.backend.problemtag.entity.ProblemTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemTagRepository extends JpaRepository<ProblemTag, Integer> {
}
