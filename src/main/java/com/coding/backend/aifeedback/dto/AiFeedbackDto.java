package com.coding.backend.aifeedback.dto;

import lombok.Data;

@Data
public class AiFeedbackDto {
    private int accuracy;
    private int efficiency;
    private int readability;
    private int test_coverage;
    private String feedback;
}
