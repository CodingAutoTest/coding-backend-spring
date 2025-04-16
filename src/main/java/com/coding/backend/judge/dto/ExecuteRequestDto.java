package com.coding.backend.judge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteRequestDto {
    private String code;
    private String language;
    private List<Integer> testcase_ids;
}
