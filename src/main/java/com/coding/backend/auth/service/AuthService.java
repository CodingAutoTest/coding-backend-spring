package com.coding.backend.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding.backend.auth.dto.UserLoginRequestDto;
import com.coding.backend.auth.dto.UserSignupRequestDto;
import com.coding.backend.user.repository.UserRepository;
import com.coding.backend.user.entity.User;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // 로그인
    public int login(UserLoginRequestDto dto) {
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
        if (optionalUser.isEmpty()) {
            return -1; // 이메일 없음
        }

        User user = optionalUser.get();
        if (!user.getPw().equals(dto.getPw())) {
            return -2; // 비밀번호 불일치
        }

        return Math.toIntExact(user.getId()); // 성공 시 ID 반환
    }

    // 회원가입
    public boolean signup(UserSignupRequestDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return false; // 중복 이메일
        }

        User user = User.builder()
                .email(dto.getEmail())
                .pw(dto.getPw())
                .name(dto.getName())
                .build();

        userRepository.save(user);
        return true;
    }
}