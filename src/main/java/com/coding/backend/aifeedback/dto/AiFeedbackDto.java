package com.coding.backend.aifeedback.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AiFeedbackDto {
    private int accuracy;
    private int efficiency;
    private int readability;
    private int testCoverage;
    private String feedback;
    private int totalScore;
}
