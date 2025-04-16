package com.coding.backend.testcase.repository;

import com.coding.backend.testcase.entity.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestcaseRepository extends JpaRepository<Testcase, Integer> {
    List<Testcase> findTop3ByProblemIdOrderByIdAsc(Integer problemId);
}

