package com.coding.backend.problem.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemResponseDto {
    private List<ProblemDto> problems;
    private int totalPages;
    private long totalElements;
}

