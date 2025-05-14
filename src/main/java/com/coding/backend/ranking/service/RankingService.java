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
        Map<Integer, Integer> ratingRankMap = userRepository.findAll(Sort.by(Sort.Direction.DESC, "rating")).stream()
                .map(User::getId)
                .collect(HashMap::new, (map, id) -> map.put(id, map.size() + 1), HashMap::putAll);

        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        Page<User> userPage = (name != null && !name.isBlank())
                ? userRepository.findByNameStartingWithIgnoreCase(name, pageable)
                : userRepository.findAll(pageable);

        List<RankingDTO> rankings = userPage.getContent().stream()
                .map(user -> {
                    RankingDTO dto = rankingMapper.toDTO(user);
                    dto.setRank(ratingRankMap.get(user.getId()));
                    return dto;
                })
                .toList();

        Map<String, Object> result = new HashMap<>();
        result.put("rankings", rankings);
        result.put("totalPages", userPage.getTotalPages());
        result.put("totalElements", userPage.getTotalElements());
        return result;
    }
}