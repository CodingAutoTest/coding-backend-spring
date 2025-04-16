package com.coding.backend.user.service;

import com.coding.backend.global.exception.ResourceNotFoundException;
import com.coding.backend.user.entity.User;
import com.coding.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String getUsernameById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 유저를 찾을 수 없습니다."));
        return user.getName();
    }
}
