package com.coding.backend.usersubmissionproblem.repository;

import com.coding.backend.usersubmissionproblem.entity.UserSubmissionProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSubmissionProblemRepository extends JpaRepository<UserSubmissionProblem, Integer> {
    boolean existsByUserIdAndProblemId(Integer userId, Integer problemId);
}
