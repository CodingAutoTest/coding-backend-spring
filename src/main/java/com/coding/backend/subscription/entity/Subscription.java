package com.coding.backend.subscription.entity;

import com.coding.backend.global.entity.BaseEntity;
import com.coding.backend.plan.entity.Plan;
import com.coding.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "auto_renewal", nullable = false)
    private Boolean autoRenewal;
    // ✅ 결제 관련 필드 (SQL에 맞게)
    @Column(name = "merchant_uid", length = 64)
    private String merchantUid;

    @Column(name = "imp_uid", length = 64)
    private String impUid;

    @Column(name = "status", length = 32)
    private String status;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "pay_method", length = 32)
    private String payMethod;
}

