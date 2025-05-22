package com.coding.backend.subscription.repository;

import com.coding.backend.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Optional<Subscription> findByUserId(Integer userId);

}
