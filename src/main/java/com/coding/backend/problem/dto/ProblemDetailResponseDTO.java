package com.coding.backend.problem.dto;

import com.coding.backend.problem.entity.Problem;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
public class ProblemDetailResponseDTO {
    private Integer id;
    private String title;
    private String description;
    private String inputConstraints;
    private String outputConstraints;
    private Integer difficulty;
    private BigDecimal acceptanceRate;
    private BigDecimal timeLimit;
    private Integer memoryLimit;

    public static ProblemDetailResponseDTO from(Problem problem) {
        return ProblemDetailResponseDTO.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .description(problem.getDescription())
                .inputConstraints(problem.getInputConstraints())
                .outputConstraints(problem.getOutputConstraints())
                .difficulty(problem.getDifficulty())
                .acceptanceRate(problem.getAcceptanceRate())
                .timeLimit(problem.getTimeLimit())
                .memoryLimit(problem.getMemoryLimit())
                .build();
    }
}

