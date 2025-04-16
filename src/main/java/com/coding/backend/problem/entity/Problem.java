package com.coding.backend.problem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "problem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Integer id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer difficulty;

    @Column(name = "acceptance_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal acceptanceRate = BigDecimal.ZERO;

    @Column(name = "input_constraints", nullable = false, columnDefinition = "TEXT")
    private String inputConstraints;

    @Column(name = "output_constraints", nullable = false, columnDefinition = "TEXT")
    private String outputConstraints;

    @Column(name = "time_limit", nullable = false, precision = 5, scale = 2)
    private BigDecimal timeLimit = BigDecimal.valueOf(2.00);

    @Column(name = "memory_limit", nullable = false)
    private Integer memoryLimit = 256;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private String updatedBy;
}
