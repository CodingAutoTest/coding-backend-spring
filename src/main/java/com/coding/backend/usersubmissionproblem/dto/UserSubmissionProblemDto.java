package com.coding.backend.usersubmissionproblem.dto;

import com.coding.backend.aifeedback.dto.AiFeedbackDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Builder
public class UserSubmissionProblemDto {
    private String judge0Status;
    private String judge0Stderr;
    private int passedCount;
    private int totalCount;
    private AiFeedbackDto aiFeedbackDto;
}

