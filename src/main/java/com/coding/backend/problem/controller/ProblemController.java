package com.coding.backend.problem.controller;

import com.coding.backend.problem.dto.ProblemDetailResponseDTO;
import com.coding.backend.problem.service.ProblemService;
import com.coding.backend.testcase.dto.TestcaseResponseDTO;
import com.coding.backend.testcase.service.TestcaseService;
import com.coding.backend.z.tools.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/problems")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProblemController {

    private final ProblemService problemService;

    /**
     * 문제 상세 조회
     * @param id 문제 ID
     * @return 문제 정보 (제목, 설명, 제한 조건 등)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResultDto<ProblemDetailResponseDTO>> getProblem(@PathVariable Integer id) {
        ProblemDetailResponseDTO result = problemService.getProblemDetail(id);
        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "문제 조회 성공", result, "result"));
    }

}
