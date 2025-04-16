package com.coding.backend.judge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitRequestDto {
    private int problem_id;
    private String language;
    private String code;
    private int user_id;
}

