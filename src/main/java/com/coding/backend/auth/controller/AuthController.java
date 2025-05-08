package com.coding.backend.auth.controller;

import com.coding.backend.auth.dto.UserLoginRequestDto;
import com.coding.backend.auth.dto.UserSignupRequestDto;
import com.coding.backend.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserSignupRequestDto dto) {
        boolean success = authService.signup(dto);
        if (success) {
            return ResponseEntity.ok("회원가입 축하합니다.");
        } else {
            return ResponseEntity.badRequest().body("중복 이메일입니다.");
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequestDto dto) {
        int result = authService.login(dto);

        if (result == -1) {
            return ResponseEntity.badRequest().body("존재하지 않는 이메일입니다.");
        } else if (result == -2) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        } else {
            return ResponseEntity.ok(result); // 유저 ID 반환
        }
    }
}