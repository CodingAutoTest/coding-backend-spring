package com.coding.backend.judge.dto;

import lombok.Data;

@Data
public class ExecuteResultDto {
    private int testcase_id;
    private String stdout;
    private String stderr;
    private double time;
    private int memory;
    private String status;
}
