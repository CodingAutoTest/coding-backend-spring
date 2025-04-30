package com.coding.backend.ranking.service;

import com.coding.backend.ranking.dto.RankingDTO;
import com.coding.backend.user.entity.User;
import com.coding.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
@Service
@RequiredArgsConstructor
public class RankingService {
    private final UserRepository userRepository;
    private final UserRepository rankingRepository;

    public RankingDTO getMyRanking(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long myRating = user.getRating() != null ? user.getRating() : 0L;
        int myRank = rankingRepository.countByRatingGreaterThan(myRating) + 1;

        return RankingDTO.builder()
                .userId(user.getId())  // ✅ 여기에 포함
                .rank(myRank)
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .rating(myRating)
                .solvedCount(user.getSolvedCount() != null ? user.getSolvedCount() : 0)
                .tier(getTier(myRating))
                .marathonDays(calculateMarathon(user.getCreatedAt()))
                .build();
    }

    public Map<String, Object> getRankingWithPageable(String sort, String order, int page, int size, String name) {
        // ✅ rating 기준 전체 랭킹 순위 맵 만들기
        Map<Integer, Integer> ratingRankMap = userRepository.findAll(Sort.by(Sort.Direction.DESC, "rating")).stream()
                .map(User::getId)
                .collect(HashMap::new, (map, id) -> map.put(id, map.size() + 1), HashMap::putAll);

        // 🔁 요청된 정렬 기준대로 유저 페이지 가져오기
        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        Page<User> userPage = (name != null && !name.isBlank())
                ? userRepository.findByNameStartingWithIgnoreCase(name, pageable)
                : userRepository.findAll(pageable);

        List<RankingDTO> rankings = userPage.getContent().stream()
                .map(user -> RankingDTO.builder()
                        .userId(user.getId())  // ✅ 여기에 포함
                        .rank(ratingRankMap.get(user.getId()))  // ✅ 항상 rating 기준 rank
                        .name(user.getName())
                        .profileImage(user.getProfileImage())
                        .rating(user.getRating())
                        .solvedCount(user.getSolvedCount())
                        .tier(getTier(user.getRating()))
                        .marathonDays(calculateMarathon(user.getCreatedAt()))
                        .build())
                .toList();

        Map<String, Object> result = new HashMap<>();
        result.put("rankings", rankings);
        result.put("totalPages", userPage.getTotalPages());
        result.put("totalElements", userPage.getTotalElements());
        return result;
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
