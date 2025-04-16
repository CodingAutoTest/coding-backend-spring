package com.coding.backend.problem.dto;

import com.coding.backend.problem.entity.Problem;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<String> tags;
}

