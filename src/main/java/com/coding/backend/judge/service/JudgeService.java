package com.coding.backend.judge.service;

import com.coding.backend.judge.dto.ExecuteRequestDto;
import com.coding.backend.judge.dto.ExecuteResponseDto;
import com.coding.backend.judge.dto.SubmitRequestDto;
import com.coding.backend.judge.dto.SubmitResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class JudgeService {

    private final RestTemplate restTemplate;

    public ExecuteResponseDto requestExecution(ExecuteRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ExecuteRequestDto> entity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ExecuteResponseDto> response = restTemplate.exchange(
                "http://114.71.147.30:28000/api/execute",
                HttpMethod.POST,
                entity,
                ExecuteResponseDto.class
        );

        return response.getBody();
    }

    public SubmitResponseDto requestSubmit(SubmitRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SubmitRequestDto> entity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<SubmitResponseDto> response = restTemplate.exchange(
                "http://114.71.147.30:28000/api/submit",
                HttpMethod.POST,
                entity,
                SubmitResponseDto.class
        );

        return response.getBody();
    }
}

