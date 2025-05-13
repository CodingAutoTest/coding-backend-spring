package com.coding.backend.subscription.service;
import com.coding.backend.plan.entity.Plan;
import com.coding.backend.plan.repository.PlanRepository;
import com.coding.backend.subscription.dto.PaymentRequestDto;
import com.coding.backend.subscription.entity.Subscription;
import com.coding.backend.subscription.repository.SubscriptionRepository;
import com.coding.backend.user.entity.User;
import com.coding.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    @Transactional
    public void saveSubscription(PaymentRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Plan plan = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));
        user.setPremiumStatus(true);
        Subscription subscription = Subscription.builder()
                .user(user)
                .plan(plan)
                .merchantUid(dto.getMerchantUid())
                .impUid(dto.getImpUid())
                .amount(dto.getAmount())
                .payMethod(dto.getPayMethod())
                .status(dto.getStatus())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1)) // 예: 1개월 기준
                .autoRenewal(false)
                .build();

        subscriptionRepository.save(subscription);
    }
}