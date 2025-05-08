package com.coding.backend.problem.entity;

import com.coding.backend.global.entity.BaseEntity;
import com.coding.backend.problemtag.entity.ProblemTag;
import com.coding.backend.usersubmissionproblem.entity.UserSubmissionProblem;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "problem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem extends BaseEntity {

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

    @Column(name = "submission_count", nullable = false)
    private Integer submissionCount = 0;

    @Column(name = "correct_count", nullable = false)
    private Integer correctCount = 0;

    @OneToMany(mappedBy = "problem", fetch = FetchType.LAZY)
    private List<ProblemTag> problemTags;

    @OneToMany(mappedBy = "problem", fetch = FetchType.LAZY)
    private List<UserSubmissionProblem> userSubmissionProblems;


}
