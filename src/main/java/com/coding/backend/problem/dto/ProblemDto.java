package com.coding.backend.problem.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemDto {
    private Integer id;
    private String title;
    private Integer difficulty;
    private BigDecimal acceptanceRate;
    private Integer status; // 유저 제출 여부에 따라 설정
}
