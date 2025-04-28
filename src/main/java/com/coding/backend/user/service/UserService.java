package com.coding.backend.user.service;

import com.coding.backend.global.exception.ResourceNotFoundException;
import com.coding.backend.user.dto.UserProblemProfileResponseDto;
import com.coding.backend.user.entity.User;
import com.coding.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserProblemProfileResponseDto getUsernameAndProfileImageById(Integer userId) {
        Optional<User> opUser = Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 유저를 찾을 수 없습니다.")));

        User user = opUser.get();

        UserProblemProfileResponseDto dto = new UserProblemProfileResponseDto();
        dto.setName(user.getName());
        dto.setProfileImage(user.getProfileImage());

        return dto;
    }
}
