package com.coding.backend.aifeedback.repository;

import com.coding.backend.aifeedback.entity.AiFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiFeedbackRepository extends JpaRepository<AiFeedback, Integer> {
}
