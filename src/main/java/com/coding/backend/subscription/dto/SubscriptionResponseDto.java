package com.coding.backend.subscription.dto;

import com.coding.backend.subscription.entity.Subscription;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SubscriptionResponseDto {
    private String planName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean autoRenewal;
    private String merchantUid;
    private String impUid;
    private String status;
    private Integer amount;
    private String payMethod;

    public static SubscriptionResponseDto from(Subscription subscription) {
        return SubscriptionResponseDto.builder()
                .planName(subscription.getPlan().getName()) // Plan 엔티티에 getName() 있어야 함
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .autoRenewal(subscription.getAutoRenewal())
                .merchantUid(subscription.getMerchantUid())
                .impUid(subscription.getImpUid())
                .status(subscription.getStatus())
                .amount(subscription.getAmount())
                .payMethod(subscription.getPayMethod())
                .build();
    }
}