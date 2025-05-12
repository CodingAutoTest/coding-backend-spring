package com.coding.backend.global.config;

import com.coding.backend.global.utils.SecurityUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class AuditorConfig {
        @Bean
        public AuditorAware<Integer> auditorProvider() {
            return () -> Optional.of(SecurityUtil.getCurrentUserId());
        }
}
