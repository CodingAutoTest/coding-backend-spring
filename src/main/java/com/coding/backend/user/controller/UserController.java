package com.coding.backend.user.controller;

import com.coding.backend.user.dto.UserMyPageDto;
import com.coding.backend.user.dto.UserProblemProfileResponseDto;
import com.coding.backend.user.service.UserService;
import com.coding.backend.global.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/nameAndImage")
    public ResponseEntity<ResultDto<UserProblemProfileResponseDto>> getUsernameAndImage() {
        UserProblemProfileResponseDto result = userService.getUsernameAndProfileImageById(1);
        return ResponseEntity.ok(ResultDto.of("result", result));
    }

    @GetMapping("/profile")
    public ResponseEntity<ResultDto<UserMyPageDto>> getUserMyPageInfo(@AuthenticationPrincipal Integer userId) {
        UserMyPageDto result = userService.getUserMyPageInfo(userId);
        return ResponseEntity.ok(ResultDto.of("result", result));
    }

    @GetMapping("/stats/level")
    public ResponseEntity<ResultDto<Map<Integer, Integer>>> getTierStats(@AuthenticationPrincipal Integer userId) {
        Map<Integer, Integer> result = userService.getTierStats(userId);
        return ResponseEntity.ok(ResultDto.of("result", result));
    }

    @GetMapping("/stats/tag")
    public ResponseEntity<ResultDto<Map<String, Integer>>> getTagStats(@AuthenticationPrincipal Integer userId) {
        Map<String, Integer> result = userService.getTagStats(userId);
        return ResponseEntity.ok(ResultDto.of("result", result));
    }

    @GetMapping("/stats/streak")
    public ResponseEntity<ResultDto<Map<LocalDate, Integer>>> getStreakStats(@AuthenticationPrincipal Integer userId) {
        Map<LocalDate, Integer> result = userService.getStreakStats(userId);
        return ResponseEntity.ok(ResultDto.of("result", result));
    }
}
