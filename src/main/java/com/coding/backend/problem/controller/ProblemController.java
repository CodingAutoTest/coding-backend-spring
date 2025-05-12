package com.coding.backend.problem.controller;

import com.coding.backend.problem.dto.ProblemDetailResponseDTO;
import com.coding.backend.problem.dto.ProblemDto;
import com.coding.backend.problem.dto.ProblemResponseDto;
import com.coding.backend.problem.service.ProblemService;
import com.coding.backend.global.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping("/{id}")
    public ResponseEntity<ResultDto<ProblemDetailResponseDTO>> getProblem(@PathVariable Integer id) {
        ProblemDetailResponseDTO result = problemService.getProblemDetail(id);
        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "문제 조회 성공", result, "result"));
    }

    @GetMapping("/{id}/difficulty")
    public ResponseEntity<ResultDto<Integer>> getDifficulty(@PathVariable Integer id) {
        Integer result = problemService.getDiffulty(id);
        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "문제 난이도 조회 성공", result, "difficulty"));
    }

    @GetMapping
    public ResponseEntity<ResultDto<ProblemResponseDto>> getProblemList(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "tier", required = false) String tier,
            @RequestParam(name = "tagId", required = false) Integer tagId,
            @RequestParam(name = "search", required = false) String search
    ) {
        Page<ProblemDto> result = problemService.getProblems(tier, tagId, search, status, page, size);

        ProblemResponseDto response = ProblemResponseDto.builder()
                .problems(result.getContent())
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .build();

        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "문제 목록 조회 성공", response, "problems"));
    }

    @PostMapping("/{id}/view")
    public ResponseEntity<ResultDto<Map<String, String>>> increaseViewCount(@PathVariable Integer id) {
        problemService.increaseViewCount(id);
        Map<String, String> result = new HashMap<>();
        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "문제 조회수 증가 성공", result, "result"));
    }
}
