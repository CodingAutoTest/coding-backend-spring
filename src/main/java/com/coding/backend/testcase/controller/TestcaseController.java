package com.coding.backend.testcase.controller;

import com.coding.backend.testcase.dto.TestcaseResponseDTO;
import com.coding.backend.testcase.service.TestcaseService;
import com.coding.backend.global.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/testcases")
public class TestcaseController {

    private final TestcaseService testcaseService;
    @GetMapping("/{testcaseId}")
    public ResponseEntity<ResultDto<?>> getTestcases(@PathVariable Integer testcaseId) {
        List<TestcaseResponseDTO> result = testcaseService.getTestcasesByProblemId(testcaseId);
        return ResponseEntity.ok(ResultDto.of("result", result));
    }
}
