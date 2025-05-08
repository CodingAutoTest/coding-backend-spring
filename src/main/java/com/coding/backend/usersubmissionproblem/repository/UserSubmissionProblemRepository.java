package com.coding.backend.usersubmissionproblem.repository;

import com.coding.backend.usersubmissionproblem.entity.UserSubmissionProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSubmissionProblemRepository extends JpaRepository<UserSubmissionProblem, Integer> {
    List<UserSubmissionProblem> findAllByUserIdAndProblemId(Integer userId, Integer problemId);
    boolean existsByUserIdAndProblemId(Integer userId, Integer problemId);
}
