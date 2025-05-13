package com.coding.backend.problem.controller;

import com.coding.backend.problem.dto.ProblemDetailResponseDTO;
import com.coding.backend.problem.dto.ProblemPageResponseDto;
import com.coding.backend.problem.service.ProblemService;
import com.coding.backend.global.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping("/{problemId}")
    public ResponseEntity<ResultDto<ProblemDetailResponseDTO>> getProblem(@PathVariable Integer problemId) {
        ProblemDetailResponseDTO result = problemService.getProblemDetail(problemId);
        return ResponseEntity.ok(ResultDto.of("result", result));
    }

    @GetMapping("/{problemId}/difficulty")
    public ResponseEntity<ResultDto<Map<String, Integer>>> getDifficulty(@PathVariable Integer problemId) {
        Integer diffulty = problemService.getDiffulty(problemId);
        Map<String, Integer> result = new HashMap<>();
        result.put("difficulty", diffulty);
        return ResponseEntity.ok(ResultDto.of("result", result));
    }

    @GetMapping
    public ResponseEntity<ResultDto<ProblemPageResponseDto>> getProblemList(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "tier", required = false) String tier,
            @RequestParam(name = "tagId", required = false) Integer tagId,
            @RequestParam(name = "search", required = false) String search,
            @AuthenticationPrincipal Integer userId
    ) {
        ProblemPageResponseDto result = problemService.getProblems(tier, tagId, search, status, page, size, userId);
        return ResponseEntity.ok(ResultDto.of("result", result));
    }

    @PostMapping("/{problemId}/view")
    public ResponseEntity<ResultDto<Map<String, Integer>>> increaseViewCount(
            @PathVariable Integer problemId) {
        Integer viewCount = problemService.increaseViewCount(problemId);
        Map<String, Integer> result = new HashMap<>();
        result.put("viewCount", viewCount);
        return ResponseEntity.ok(ResultDto.of("result", result));
    }
}
