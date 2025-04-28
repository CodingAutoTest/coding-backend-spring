package com.coding.backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class AuditorConfig {

    @Bean
    public AuditorAware<Long> auditorProvider() {
        // 지금은 더미 유저 ID 1L로 넣음
        return () -> Optional.of(1L);
    }

    // Spring Security 설정하면 알아서 userId 들어가도록
//    @Bean
//    public AuditorAware<Long> auditorProvider() {
//        return () -> Optional.of(SecurityUtil.getCurrentUserId());
//    }

}
