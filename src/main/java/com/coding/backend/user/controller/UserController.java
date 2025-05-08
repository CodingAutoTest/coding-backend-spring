package com.coding.backend.user.controller;

import com.coding.backend.user.dto.UserProblemProfileResponseDto;
import com.coding.backend.user.service.UserService;
import com.coding.backend.global.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/nameAndImage")
    public ResponseEntity<ResultDto<UserProblemProfileResponseDto>> getUsernameAndImage() {
        UserProblemProfileResponseDto dto = userService.getUsernameAndProfileImageById(1);
        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "유저 이름, IMAGE 조회 성공", dto, "result"));
    }
}
