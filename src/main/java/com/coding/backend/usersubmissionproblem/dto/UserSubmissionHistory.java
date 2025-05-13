package com.coding.backend.usersubmissionproblem.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserSubmissionHistory {
    private int submissionId;
    private String createdAt;
    private String language;
    private BigDecimal executionTime;
    private int memoryUsed;
    private boolean status;
}
