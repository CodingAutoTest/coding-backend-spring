package com.coding.backend.usersubmissionproblem.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserSubmissionProblemDto {
    private String error;
    private int passedCount;
    private int totalCount;
    private SubmissionScore scores;
    private String feedback;
    private int totalScore;
}

