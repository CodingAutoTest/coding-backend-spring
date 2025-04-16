package com.coding.backend.ranking.service;

import com.coding.backend.ranking.dto.RankingDTO;
import com.coding.backend.ranking.repository.RankingRepository;
import com.coding.backend.user.entity.User;
import com.coding.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
@Service
@RequiredArgsConstructor
public class RankingService {
    private final UserRepository userRepository;
    private final RankingRepository rankingRepository;

    public RankingDTO getMyRanking(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long myRating = user.getRating() != null ? user.getRating() : 0L;
        int myRank = rankingRepository.countByRatingGreaterThan(myRating) + 1;

        return RankingDTO.builder()
                .rank(myRank)
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .rating(myRating)
                .solvedCount(user.getSolvedCount() != null ? user.getSolvedCount() : 0)
                .tier(getTier(myRating))
                .marathonDays(calculateMarathon(user.getCreatedAt()))
                .build();
    }
    public  List<RankingDTO> getTopUsers() {
        List<User> users = rankingRepository.findTop100ByOrderByRatingDesc();

        return IntStream.range(0, users.size())
                .mapToObj(i -> {
                    User user = users.get(i);
                    return RankingDTO.builder()
                            .rank(i + 1)
                            .name(user.getName())
                            .profileImage(user.getProfileImage())
                            .rating(user.getRating() != null ? user.getRating() : 0L)
                            .solvedCount(user.getSolvedCount() != null ? user.getSolvedCount() : 0)
                            .tier(getTier(user.getRating()))
                            .marathonDays(calculateMarathon(user.getCreatedAt()))
                            .build();
                })
                .toList();
    }

    private String getTier(Long rating) {
        if (rating == null) return "브론즈";
        if (rating >= 3300) return "마스터";
        if (rating >= 3200) return "다이아";
        if (rating >= 3100) return "플래티넘";
        if (rating >= 3000) return "골드";
        if (rating >= 2900) return "실버";
        return "브론즈";
    }

    private long calculateMarathon(LocalDateTime createdAt) {
        if (createdAt == null) return 0;
        return Duration.between(createdAt, LocalDateTime.now()).toDays();
    }
}
