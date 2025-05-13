package com.coding.backend.user.controller;

import com.coding.backend.global.dto.UploadImageResponseDto;
import com.coding.backend.user.dto.*;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/nameAndImage")
    public ResponseEntity<ResultDto<UserProblemProfileResponseDto>> getUsernameAndImage(
            @AuthenticationPrincipal Integer userId) {
        UserProblemProfileResponseDto result = userService.getUsernameAndProfileImageById(userId);
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

    @PostMapping("/{userId}/upload-image")
    public ResponseEntity<ResultDto<UploadImageResponseDto>> uploadImages(
            @PathVariable Integer userId,
            @RequestPart(value = "profileImage",    required = false) MultipartFile profileImg,
            @RequestPart(value = "backgroundImage", required = false) MultipartFile bgImg) {

        UploadImageResponseDto dto = userService.uploadImage(userId, profileImg, bgImg);
        return ResponseEntity.ok(
                ResultDto.of(HttpStatus.OK, "이미지 업로드 및 DB 저장 성공", dto, "urls"));
    }

    @PostMapping("/{userId}/modify")
    public ResponseEntity<ResultDto<Void>> userModifyProfile(
            @PathVariable Integer userId,
            @RequestBody UserProfileModifyRequestDto dto) {

        userService.modifyProfile(userId, dto);
        return ResponseEntity.ok(
                ResultDto.of(HttpStatus.OK, "프로필 수정 완료"));
    }

    @PostMapping("/{userId}/change-password")
    public ResponseEntity<ResultDto<Void>> userModifyPassword(
            @PathVariable Integer userId,
            @RequestBody UserPasswordChangeRequestDto dto) {

        userService.modifyPassword(userId, dto);
        return ResponseEntity.ok(
                ResultDto.of(HttpStatus.OK, "비밀번호 변경 완료"));
    }

    @PostMapping("/{userId}/save")
    public ResponseEntity<ResultDto<Void>> userSavePremium(
            @PathVariable Integer userId,
            @RequestBody PremiumRequestDto dto) {

        userService.savePremium(userId, dto);
        return ResponseEntity.ok(
                ResultDto.of(HttpStatus.OK, "프리미엄 상태 변경 완료"));
    }

    @PostMapping("/{userId}/remove")
    public ResponseEntity<ResultDto<Void>> userRemove(@PathVariable Integer userId) {
        userService.removeUser(userId);
        return ResponseEntity.ok(
                ResultDto.of(HttpStatus.OK, "계정 삭제 완료"));
    }
}
