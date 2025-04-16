package com.coding.backend.judge.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExecuteResponseDto {
    private List<ExecuteResultDto> results;
}
