package com.coding.backend.judge.controller;

import com.coding.backend.judge.dto.ExecuteRequestDto;
import com.coding.backend.judge.dto.ExecuteResponseDto;
import com.coding.backend.judge.dto.SubmitRequestDto;
import com.coding.backend.judge.dto.SubmitResponseDto;
import com.coding.backend.judge.service.JudgeService;
import com.coding.backend.global.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/judges")
@RequiredArgsConstructor
public class JudgeController {

    private final JudgeService judgeService;

    @PostMapping("/execute")
    public ResponseEntity<ResultDto<ExecuteResponseDto>> executeCode(@RequestBody ExecuteRequestDto requestDto) {
        ExecuteResponseDto result = judgeService.requestExecution(requestDto);
        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "실행 성공", result, "result"));
    }

    @PostMapping("/submit")
    public ResponseEntity<ResultDto<SubmitResponseDto>> submitCode(@RequestBody SubmitRequestDto requestDto) {
        requestDto.setUser_id(1);
        SubmitResponseDto result = judgeService.requestSubmit(requestDto);

            return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "채점 성공", result, "result"));
    }

}
