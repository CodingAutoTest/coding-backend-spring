package com.coding.backend.testcase.controller;

import com.coding.backend.testcase.dto.TestcaseResponseDTO;
import com.coding.backend.testcase.service.TestcaseService;
import com.coding.backend.z.tools.ResultDto;
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
@RequestMapping
public class TestcaseController {

    private final TestcaseService testcaseService;
    @GetMapping("/testcases")
    public ResponseEntity<ResultDto<?>> getTestcases(@PathVariable Integer id) {
        List<TestcaseResponseDTO> result = testcaseService.getTestcasesByProblemId(id);
        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "테스트케이스 조회 성공", result, "result"));
    }
}
