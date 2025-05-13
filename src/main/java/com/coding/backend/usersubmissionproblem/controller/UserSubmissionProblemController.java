package com.coding.backend.usersubmissionproblem.controller;

import com.coding.backend.global.dto.ResultDto;
import com.coding.backend.usersubmissionproblem.dto.UserSubmissionProblemDto;
import com.coding.backend.usersubmissionproblem.dto.UserSubmissionHistory;
import com.coding.backend.usersubmissionproblem.service.UserSubmissionProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/{submissionId}")
    public ResponseEntity<ResultDto<UserSubmissionProblemDto>> submissionDetails(
            @PathVariable Integer submissionId) {
        UserSubmissionProblemDto result = userSubmissionProblemService.findSubmissionDetails(submissionId);
        System.out.println(result.getAiFeedbackDto().getTotalScore());
        return ResponseEntity.ok(ResultDto.of("result", result));
    }

    @GetMapping("/history/{submissionId}")
    public ResponseEntity<ResultDto<List<UserSubmissionHistory>>> submissionList(
            @AuthenticationPrincipal Integer userId, @PathVariable Integer submissionId) {
        List<UserSubmissionHistory> result = userSubmissionProblemService
                .findSubmissionHistoryList(userId, submissionId);
        return ResponseEntity.ok(ResultDto.of("result", result));
    }

    @GetMapping("/code/{submissionId}")
    public ResponseEntity<ResultDto<String>> submissionCodeDetails(
            @PathVariable Integer submissionId) {
        String result = userSubmissionProblemService.findSubmissionCode(submissionId);
        return ResponseEntity.ok(ResultDto.of("result", result));
    }
}
