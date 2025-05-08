package com.coding.backend.usersubmissionproblem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder // Builder와 Setter는 같이 선언하지 않음
public class SubmissionScore {
    private int accuracy;
    private int efficiency;
    private int readability;
    private int testCoverage;

}
