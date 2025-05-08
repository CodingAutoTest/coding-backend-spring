package com.coding.backend.problem.repository;

import com.coding.backend.problem.entity.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @Query("""
    SELECT DISTINCT p FROM Problem p
    LEFT JOIN p.problemTags pt
    LEFT JOIN UserSubmissionProblem usp
        ON usp.problem = p AND usp.user.id = :userId
    WHERE (:tier IS NULL OR p.difficulty = :tier)
      AND (:tagId IS NULL OR pt.tag.id = :tagId)
      AND (:search IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')))
      AND (
            :status IS NULL
            OR (:status = -1 AND usp IS NULL)
            OR (:status = 0 AND usp IS NOT NULL AND usp.status = false)
            OR (:status = 1 AND usp IS NOT NULL AND usp.status = true)
          )
    """)
    Page<Problem> findFilteredProblems(
            @Param("status") Integer status,
            @Param("tier") Integer tier,
            @Param("search") String search,
            @Param("tagId") Long tagId,
            @Param("userId") Integer userId,
            Pageable pageable
    );





}
