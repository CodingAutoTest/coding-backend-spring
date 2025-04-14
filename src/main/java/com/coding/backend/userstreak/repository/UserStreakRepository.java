package com.coding.backend.userstreak.repository;

import com.coding.backend.userstreak.entity.UserStreak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStreakRepository extends JpaRepository<UserStreak, Integer> {
}
