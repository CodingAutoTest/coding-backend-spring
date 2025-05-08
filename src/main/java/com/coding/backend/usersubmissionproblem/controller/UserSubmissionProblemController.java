package com.coding.backend.usersubmissionproblem.controller;

import com.coding.backend.global.dto.ResultDto;
import com.coding.backend.usersubmissionproblem.dto.UserSubmissionProblemDto;
import com.coding.backend.usersubmissionproblem.dto.UserSubmissionHistory;
import com.coding.backend.usersubmissionproblem.service.UserSubmissionProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("submissions")
@RequiredArgsConstructor
public class UserSubmissionProblemController {

    private final UserSubmissionProblemService userSubmissionProblemService;

    @GetMapping("/{id}")
    public ResponseEntity<ResultDto<UserSubmissionProblemDto>> submissionDetails(@PathVariable Integer id) {
        UserSubmissionProblemDto result = userSubmissionProblemService.findSubmissionDetails(id);
        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "제출 내역 상세 조회 성공", result, "result"));
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<ResultDto<List<UserSubmissionHistory>>> submissionList(@PathVariable Integer id) {
        List<UserSubmissionHistory> result = userSubmissionProblemService.findSubmissionHistoryList(1, id);
        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "제출 내역 조회 성공", result, "result"));
    }

    @GetMapping("/code/{id}")
    public ResponseEntity<ResultDto<String>> submissionCodeDetails(@PathVariable Integer id) {
        String result = userSubmissionProblemService.findSubmissionCode(id);
        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "제출 내역 코드 조회 성공", result, "result"));
    }
}
