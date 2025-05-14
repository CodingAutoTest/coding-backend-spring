package com.coding.backend.subscription.controller;

import com.coding.backend.global.dto.ResultDto;
import com.coding.backend.subscription.dto.PaymentRequestDto;
import com.coding.backend.subscription.service.SubscriptionService;
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
}