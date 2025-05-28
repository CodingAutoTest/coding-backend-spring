package com.coding.backend.user.controller;

import com.coding.backend.global.dto.ResultDto;
import com.coding.backend.global.dto.UploadImageResponseDto;
import com.coding.backend.user.dto.*;
import com.coding.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /* ─── 기본 정보 ─── */

    @GetMapping("/nameAndImage")
    public ResponseEntity<ResultDto<UserProblemProfileResponseDto>> getUsernameAndImage(
            @AuthenticationPrincipal Integer userId) {

        var dto = userService.getUsernameAndProfileImageById(userId);
        return ResponseEntity.ok(ResultDto.of("result", dto));
    }

    @GetMapping("/profile")
    public ResponseEntity<ResultDto<UserMyPageDto>> getUserMyPageInfo(
            @AuthenticationPrincipal Integer userId) {

        var dto = userService.getUserMyPageInfo(userId);
        return ResponseEntity.ok(ResultDto.of("result", dto));
    }

    @GetMapping("/profile/{name}")
    public ResponseEntity<ResultDto<UserMyPageDto>> otherProfile(@PathVariable String name) {
        return ResponseEntity.ok(ResultDto.of("result", userService.getUserMyPageInfoByName(name)));
    }

    /* ─── 통계 ─── */

    @GetMapping("/stats/level")
    public ResponseEntity<ResultDto<Map<Integer, Integer>>> getTierStats(
            @AuthenticationPrincipal Integer userId) {

        var dto = userService.getTierStats(userId);
        return ResponseEntity.ok(ResultDto.of("result", dto));
    }

    @GetMapping("/stats/tag")
    public ResponseEntity<ResultDto<Map<String, Integer>>> getTagStats(
            @AuthenticationPrincipal Integer userId) {

        var dto = userService.getTagStats(userId);
        return ResponseEntity.ok(ResultDto.of("result", dto));
    }

    @GetMapping("/stats/streak")
    public ResponseEntity<ResultDto<Map<LocalDate, Integer>>> getStreakStats(
            @AuthenticationPrincipal Integer userId) {

        var dto = userService.getStreakStats(userId);
        return ResponseEntity.ok(ResultDto.of("result", dto));
    }

    /* ─── 이미지 업로드 ─── */
    @PostMapping("/upload-image")
    public ResponseEntity<ResultDto<UploadImageResponseDto>> uploadImages(
            @AuthenticationPrincipal Integer userId,
            @RequestPart(value = "profileImage",    required = false) MultipartFile profileImg,
            @RequestPart(value = "backgroundImage", required = false) MultipartFile bgImg) {

        var dto = userService.uploadImage(userId, profileImg, bgImg);
        return ResponseEntity.ok(ResultDto.of("urls", dto));
    }

    /* ─── 프로필 수정 ─── */

    @PostMapping("/modify")
    public ResponseEntity<ResultDto<String>> modifyProfile(
            @AuthenticationPrincipal Integer userId,
            @RequestBody UserProfileModifyRequestDto dto) {

        userService.modifyProfile(userId, dto);
        return ResponseEntity.ok(ResultDto.of("result", ""));
    }

    /* ─── 비밀번호 변경 ─── */
    @PostMapping("/change-password")
    public ResponseEntity<ResultDto<String>> changePassword(
            @AuthenticationPrincipal Integer userId,
            @RequestBody UserPasswordChangeRequestDto dto) {

        userService.modifyPassword(userId, dto);
        return ResponseEntity.ok(ResultDto.of("result", ""));
    }

    /* ─── 프리미엄 저장 ─── */
    @PostMapping("/save")
    public ResponseEntity<ResultDto<String>> savePremium(
            @AuthenticationPrincipal Integer userId,
            @RequestBody PremiumRequestDto dto) {

        userService.savePremium(userId, dto);
        return ResponseEntity.ok(ResultDto.of("result", ""));
    }

    /* ─── 계정 삭제 ─── */
    @PostMapping("/remove")
    public ResponseEntity<ResultDto<String>> removeUser(
            @AuthenticationPrincipal Integer userId) {

        userService.removeUser(userId);
        return ResponseEntity.ok(ResultDto.of("result", ""));
    }
}
