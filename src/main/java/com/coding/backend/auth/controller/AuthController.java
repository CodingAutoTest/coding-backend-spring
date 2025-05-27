package com.coding.backend.auth.controller;

import com.coding.backend.auth.dto.UserLoginRequestDto;
import com.coding.backend.auth.dto.UserSignupRequestDto;
import com.coding.backend.auth.service.AuthService;
import com.coding.backend.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j // ✅ 로그 출력을 위한 Lombok 어노테이션
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserSignupRequestDto dto) {
        boolean success = authService.signup(dto);
        return success ?
                ResponseEntity.ok().body("회원가입 완료") :
                ResponseEntity.badRequest().body("이미 등록된 이메일입니다.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequestDto dto) {
        Integer userId = authService.login(dto);

        if (userId == 0) {
            log.warn("로그인 실패 - 이메일: {}", dto.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        String token = jwtTokenProvider.createToken(userId);
        log.info("로그인 성공 - 사용자 ID: {}", userId);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .body("로그인 성공");
    }

    // 비밀번호 재설정
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");
            log.info("비밀번호 재설정 요청 - 이메일: {}", email);
            authService.sendResetPasswordEmail(email);
            return ResponseEntity.ok("메일 발송 완료");
        } catch (IllegalArgumentException e) {
            log.warn("비밀번호 재설정 실패 - 잘못된 요청: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            log.error("비밀번호 재설정 실패 - 메일 전송 오류: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("비밀번호 재설정 실패 - 알 수 없는 서버 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }
}