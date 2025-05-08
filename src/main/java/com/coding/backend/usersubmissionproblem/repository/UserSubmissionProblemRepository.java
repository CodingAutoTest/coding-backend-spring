package com.coding.backend.usersubmissionproblem.repository;

import com.coding.backend.usersubmissionproblem.entity.UserSubmissionProblem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSubmissionProblemRepository extends JpaRepository<UserSubmissionProblem, Integer> {
    List<UserSubmissionProblem> findByUserIdAndStatusTrue(Integer userId);
}
