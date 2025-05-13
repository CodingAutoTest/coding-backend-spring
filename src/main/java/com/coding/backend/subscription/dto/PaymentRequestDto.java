package com.coding.backend.subscription.dto;

// dto/PaymentRequestDto.java
import lombok.Getter;

@Getter
public class PaymentRequestDto {
    private String impUid;
    private String merchantUid;
    private int amount;
    private String payMethod;
    private String status;
    private int userId;
    private int planId;
}