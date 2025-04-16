package com.coding.backend.ranking.repository;

import com.coding.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankingRepository extends JpaRepository<User, Integer> {
    List<User> findTop100ByOrderByRatingDesc();
    int countByRatingGreaterThan(Long rating);

}
