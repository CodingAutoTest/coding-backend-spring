package com.coding.backend.problem.repository;

import com.coding.backend.problem.entity.Problem;
import com.coding.backend.problemtag.dto.ProblemTagDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

    @Query("""
        SELECT p FROM Problem p
        LEFT JOIN FETCH p.problemTags pt
        LEFT JOIN FETCH pt.tag
        WHERE p.id = :problemId
    """)
    Optional<Problem> findWithTagsById(@Param("problemId") Integer problemId);

}
