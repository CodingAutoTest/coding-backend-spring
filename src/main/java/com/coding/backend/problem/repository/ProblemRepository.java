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

    // 유저 ID가 있을 때: status 조건 포함 + status 순 정렬
    @Query("""
        SELECT DISTINCT p FROM Problem p
        LEFT JOIN p.problemTags pt
        WHERE (
            :tier IS NULL OR
            (:tier = 'bronze' AND p.difficulty BETWEEN 1 AND 5) OR
            (:tier = 'silver' AND p.difficulty BETWEEN 6 AND 10) OR
            (:tier = 'gold' AND p.difficulty BETWEEN 11 AND 15) OR
            (:tier = 'platinum' AND p.difficulty BETWEEN 16 AND 20) OR
            (:tier = 'diamond' AND p.difficulty BETWEEN 21 AND 25) OR
            (:tier = 'master' AND p.difficulty BETWEEN 26 AND 30)
        )
        AND (:tagId IS NULL OR pt.tag.id = :tagId)
        AND (:search IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (
            :status IS NULL OR
            (
                :status = 'unsolved'
                AND NOT EXISTS (
                    SELECT 1 FROM UserSubmissionProblem u
                    WHERE u.problem = p AND u.user.id = :userId
                )
            )
            OR (
                :status = 'solving'
                AND EXISTS (
                    SELECT 1 FROM UserSubmissionProblem u
                    WHERE u.problem = p AND u.user.id = :userId
                )
                AND NOT EXISTS (
                    SELECT 1 FROM UserSubmissionProblem u
                    WHERE u.problem = p AND u.user.id = :userId AND u.status = true
                )
            )
            OR (
                :status = 'solved'
                AND EXISTS (
                    SELECT 1 FROM UserSubmissionProblem u
                    WHERE u.problem = p AND u.user.id = :userId AND u.status = true
                )
            )
        )
        ORDER BY
            CASE
                WHEN EXISTS (
                    SELECT 1 FROM UserSubmissionProblem u
                    WHERE u.problem = p AND u.user.id = :userId AND u.status = true
                ) THEN 2
                WHEN EXISTS (
                    SELECT 1 FROM UserSubmissionProblem u
                    WHERE u.problem = p AND u.user.id = :userId
                ) THEN 0
                ELSE 1
            END ASC,
            p.id ASC
    """)
    Page<Problem> findFilteredProblemsWithUser(
            @Param("status") String status,
            @Param("tier") String tier,
            @Param("search") String search,
            @Param("tagId") Integer tagId,
            @Param("userId") Integer userId,
            Pageable pageable
    );

    // 유저 ID가 없을 때: 단순 필터링 (status 조건 없음), 정렬 없음
    @Query("""
        SELECT DISTINCT p FROM Problem p
        LEFT JOIN p.problemTags pt
        WHERE (
            :tier IS NULL OR
            (:tier = 'bronze' AND p.difficulty BETWEEN 1 AND 5) OR
            (:tier = 'silver' AND p.difficulty BETWEEN 6 AND 10) OR
            (:tier = 'gold' AND p.difficulty BETWEEN 11 AND 15) OR
            (:tier = 'platinum' AND p.difficulty BETWEEN 16 AND 20) OR
            (:tier = 'diamond' AND p.difficulty BETWEEN 21 AND 25) OR
            (:tier = 'master' AND p.difficulty BETWEEN 26 AND 30)
        )
        AND (:tagId IS NULL OR pt.tag.id = :tagId)
        AND (:search IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')))
        ORDER BY p.id ASC
    """)
    Page<Problem> findFilteredProblemsWithoutUser(
            @Param("tier") String tier,
            @Param("search") String search,
            @Param("tagId") Integer tagId,
            Pageable pageable
    );
}
