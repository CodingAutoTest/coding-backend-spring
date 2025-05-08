package com.coding.backend.user.controller;

import com.coding.backend.user.dto.UserMyPageDto;
import com.coding.backend.user.dto.UserProblemProfileResponseDto;
import com.coding.backend.user.service.UserService;
import com.coding.backend.global.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/nameAndImage")
    public ResponseEntity<ResultDto<UserProblemProfileResponseDto>> getUsernameAndImage() {
        UserProblemProfileResponseDto dto = userService.getUsernameAndProfileImageById(1);
        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "유저 이름, IMAGE 조회 성공", dto, "result"));
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<ResultDto<UserMyPageDto>> getUserMyPageInfo(@PathVariable Integer userId) {
        UserMyPageDto dto = userService.getUserMyPageInfo(userId);
        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "마이페이지 조회 성공", dto, "user"));
    }

    @GetMapping("/{userId}/stats/level")
    public ResponseEntity<ResultDto<Map<Integer, Integer>>> getTierStats(@PathVariable Integer userId) {
        Map<Integer, Integer> data = userService.getTierStats(userId);
        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "티어별 통계 조회 성공", data, "levelStats"));
    }

    @GetMapping("/{userId}/stats/tag")
    public ResponseEntity<ResultDto<Map<String, Integer>>> getTagStats(@PathVariable Integer userId) {
        Map<String, Integer> data = userService.getTagStats(userId);
        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "태그별 통계 조회 성공", data, "tagStats"));
    }

    @GetMapping("/{userId}/stats/streak")
    public ResponseEntity<ResultDto<Map<LocalDate, Integer>>> getStreakStats(@PathVariable Integer userId) {
        Map<LocalDate, Integer> data = userService.getStreakStats(userId);
        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "스트릭 통계 조회 성공", data, "streakStats"));
    }
}
