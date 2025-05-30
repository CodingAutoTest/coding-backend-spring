package com.coding.backend.usersubmissionproblem.entity;

import com.coding.backend.aifeedback.entity.AiFeedback;
import com.coding.backend.global.entity.BaseEntity;
import com.coding.backend.language.entity.Language;
import com.coding.backend.problem.entity.Problem;
import com.coding.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_submission_problem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSubmissionProblem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_submission_problem_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ai_feedback_id", nullable = false)
    private AiFeedback aiFeedback;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Column(name = "memory_used", nullable = false)
    private Integer memoryUsed;

    @Column(name = "submission_code", nullable = false, columnDefinition = "TEXT")
    private String submissionCode;

    @Column(name = "execution_time", nullable = false, precision = 5, scale = 2)
    private BigDecimal executionTime;

    @Column(nullable = false)
    private Boolean status;

    @Column(name = "passed_count")
    private Integer passedCount;

    @Column(name = "total_count")
    private Integer totalCount;

    @Column(name = "judge0_status", columnDefinition = "TEXT")
    private String judge0Status;

    @Column(name = "judge0_stderr", columnDefinition = "TEXT")
    private String judge0Stderr;
}
