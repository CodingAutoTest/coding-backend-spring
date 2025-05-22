package com.coding.backend.ranking.service;

import com.coding.backend.ranking.dto.RankingDTO;
import com.coding.backend.ranking.mapper.RankingMapper;
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
    private final RankingMapper rankingMapper;

    public RankingDTO getMyRanking(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long myRating = user.getRating() != null ? user.getRating() : 0L;
        int myRank = rankingRepository.countByRatingGreaterThan(myRating) + 1;

        RankingDTO dto = rankingMapper.toDTO(user);
        dto.setRank(myRank); // 직접 순위 주입
        return dto;
    }

    public Map<String, Object> getRankingWithPageable(String sort, String order, int page, int size, String name) {
        // 1. 모든 유저를 rating 기준으로 내림차순 정렬
        List<User> sortedUsers = userRepository.findAll(Sort.by(Sort.Direction.DESC, "rating"));

        // 2. 유저 ID별로 랭킹을 부여 (동일 rating이면 동일 랭킹)
        Map<Integer, Integer> ratingRankMap = new HashMap<>();
        long prevRating = Long.MIN_VALUE;
        int currentRank = 0;
        int sameRatingCount = 0;

        for (int i = 0; i < sortedUsers.size(); i++) {
            User user = sortedUsers.get(i);
            long rating = user.getRating() != null ? user.getRating() : 0L;

            if (rating != prevRating) {
                currentRank += sameRatingCount;
                sameRatingCount = 1;
                prevRating = rating;
            } else {
                sameRatingCount++;
            }

            ratingRankMap.put(user.getId(), currentRank + 1);
        }

        // 3. 정렬 방향 설정
        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        // 4. 페이지네이션 적용한 유저 목록 조회 (이름 검색 포함 여부)
        Page<User> userPage = (name != null && !name.isBlank())
                ? userRepository.findByNameStartingWithIgnoreCase(name, pageable)
                : userRepository.findAll(pageable);

        // 5. DTO 변환 + 랭킹 주입
        List<RankingDTO> rankings = userPage.getContent().stream()
                .map(user -> {
                    RankingDTO dto = rankingMapper.toDTO(user);
                    dto.setRank(ratingRankMap.get(user.getId()));
                    return dto;
                })
                .toList();

        // 6. 결과 구성
        Map<String, Object> result = new HashMap<>();
        result.put("rankings", rankings);
        result.put("totalPages", userPage.getTotalPages());
        result.put("totalElements", userPage.getTotalElements());

        return result;
    }
}