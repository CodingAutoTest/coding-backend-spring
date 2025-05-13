package com.coding.backend.subscription.controller;

import com.coding.backend.global.dto.ResultDto;
import com.coding.backend.subscription.dto.PaymentRequestDto;
import com.coding.backend.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/payment")
    public ResponseEntity<ResultDto<Void>> handlePayment(@RequestBody PaymentRequestDto dto) {
        subscriptionService.saveSubscription(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResultDto.of(HttpStatus.OK, "✅ 결제 정보 저장 성공"));
    }
}