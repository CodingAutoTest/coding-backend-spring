package com.coding.backend.user.service;

import com.coding.backend.global.exception.ResourceNotFoundException;
import com.coding.backend.global.utils.EntityUtils;
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

    public UserProblemProfileResponseDto getUsernameAndProfileImageById(Integer id) {
        User user = EntityUtils.getByIdOrThrow(userRepository, id, "유저가 없습니다.");

        UserProblemProfileResponseDto dto = new UserProblemProfileResponseDto();
        dto.setName(user.getName());
        dto.setProfileImage(user.getProfileImage());

        return dto;
    }
}
