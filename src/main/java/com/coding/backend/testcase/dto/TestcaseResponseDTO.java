package com.coding.backend.testcase.dto;

import com.coding.backend.testcase.entity.Testcase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TestcaseResponseDTO {
    private Integer id;
    private String input;
    private String output;

    public static TestcaseResponseDTO from(Testcase entity) {
        return TestcaseResponseDTO.builder()
                .id(entity.getId())
                .input(entity.getInput())
                .output(entity.getOutput())
                .build();
    }
}

