package com.coding.backend.usersubmissionproblem.entity;

import com.cat.domain.user.entity.User;
import com.coding.backend.aifeedback.entity.AiFeedback;
import com.coding.backend.language.entity.Language;
import com.coding.backend.problem.entity.Problem;
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
public class UserSubmissionProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ai_feedbak_id", nullable = false)
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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private String updatedBy;
}
