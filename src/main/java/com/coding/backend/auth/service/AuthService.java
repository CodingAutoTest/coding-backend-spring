package com.coding.backend.auth.service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coding.backend.auth.dto.UserLoginRequestDto;
import com.coding.backend.auth.dto.UserSignupRequestDto;
import com.coding.backend.user.repository.UserRepository;
import com.coding.backend.user.entity.User;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 로그인
    public Integer login(UserLoginRequestDto dto) {
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(dto.getPw(), user.getPw())) {
                return user.getId();
            }
        }
        return 0; // 로그인 실패
    }


    // 회원가입
    public boolean signup(UserSignupRequestDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return false;
        }

        User user = User.builder()
                .email(dto.getEmail())
                .pw(passwordEncoder.encode(dto.getPw()))  // 해시 처리
                .name(dto.getName())
                .build();

        userRepository.save(user);
        return true;
    }
}