package com.coding.backend.subscription.controller;

import com.coding.backend.global.dto.ResultDto;
import com.coding.backend.global.utils.SecurityUtil;
import com.coding.backend.subscription.dto.PaymentRequestDto;
import com.coding.backend.subscription.dto.SubscriptionResponseDto;
import com.coding.backend.subscription.entity.Subscription;
import com.coding.backend.subscription.service.SubscriptionService;
import com.coding.backend.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/payment")
    public ResponseEntity<ResultDto<Void>> handlePayment(@RequestBody PaymentRequestDto dto,@AuthenticationPrincipal Integer userId) {

        dto.setUserId(userId);
        subscriptionService.saveSubscription(dto);
        return ResponseEntity.ok().build(); // 상태코드 200 OK만 반환

    }
    @GetMapping("/me")
    public ResponseEntity<ResultDto<SubscriptionResponseDto>> getUser() {
        Integer userId =  SecurityUtil.getCurrentUserId();
        System.out.println(userId);
        if (userId == null) {
            // ⚠ null 넘기면 Map.of에서 NPE 발생하므로, 따로 처리
            return ResponseEntity.ok(new ResultDto<>());
        }
        Subscription subscription = subscriptionService.getByUserId(userId);

        if (subscription == null) {
            System.out.println(subscription);
            return ResponseEntity.ok(new ResultDto<>()); // null-safe
        }
        System.out.println(subscription);

        SubscriptionResponseDto dto = SubscriptionResponseDto.from(subscription);
        return ResponseEntity.ok(ResultDto.of("result", dto));
    }
}