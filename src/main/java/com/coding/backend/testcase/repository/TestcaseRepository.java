package com.coding.backend.testcase.repository;

import com.coding.backend.testcase.entity.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestcaseRepository extends JpaRepository<Testcase, Integer> {
}
