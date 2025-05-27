package com.coding.backend.auth.service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coding.backend.auth.dto.UserLoginRequestDto;
import com.coding.backend.auth.dto.UserSignupRequestDto;
import com.coding.backend.user.repository.UserRepository;
import com.coding.backend.user.entity.User;

@Service
@RequiredArgsConstructor
@Slf4j // ✅ Lombok 로그 어노테이션
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

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
                .pw(passwordEncoder.encode(dto.getPw()))
                .name(dto.getName())
                .build();

        userRepository.save(user);
        user.setCreatedBy(user.getId());
        userRepository.save(user);
        return true;
    }

    // 비밀번호 재설정 메일 발송
    public void sendResetPasswordEmail(String email) {
        log.info("비밀번호 재설정 요청 이메일: {}", email);

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            log.warn("해당 이메일이 존재하지 않음: {}", email);
            throw new IllegalArgumentException("가입된 이메일이 아닙니다.");
        }

        String tempPassword = generateTempPassword();
        User user = optionalUser.get();
        user.setPw(passwordEncoder.encode(tempPassword));
        userRepository.save(user);
        log.info("임시 비밀번호 생성 및 저장 완료: {}", tempPassword);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("[CAT] 비밀번호 재설정 안내");
            message.setText("임시 비밀번호: " + tempPassword + "\n로그인 후 반드시 비밀번호를 변경해주세요.");
            mailSender.send(message);
            log.info("이메일 전송 완료: {}", email);
        } catch (Exception e) {
            log.error("이메일 전송 실패: {}", e.getMessage(), e);
            throw new RuntimeException("메일 전송 실패: " + e.getMessage());
        }
    }

    private String generateTempPassword() {
        return Long.toHexString(Double.doubleToLongBits(Math.random())).substring(0, 8);
    }
}