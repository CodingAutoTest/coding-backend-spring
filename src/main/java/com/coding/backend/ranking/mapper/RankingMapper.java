// src/main/java/com/coding/backend/ranking/mapper/RankingMapper.java
package com.coding.backend.ranking.mapper;

import com.coding.backend.ranking.dto.RankingDTO;
import com.coding.backend.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.Duration;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RankingMapper {

    @Mapping(target = "tier", source = "rating", qualifiedByName = "mapTier")
    @Mapping(target = "marathonDays", source = "createdAt", qualifiedByName = "mapMarathon")
    @Mapping(target = "rank", ignore = true)// 순위는 외부에서 수동 주입
    @Mapping(target = "userId", source = "id")
    RankingDTO toDTO(User user);

    @Named("mapTier")
    default String mapTier(Long rating) {
        if (rating == null) return "브론즈";
        if (rating >= 3300) return "마스터";
        if (rating >= 3200) return "다이아";
        if (rating >= 3100) return "플래티넘";
        if (rating >= 3000) return "골드";
        if (rating >= 2900) return "실버";
        return "브론즈";
    }

    @Named("mapMarathon")
    default long mapMarathon(LocalDateTime createdAt) {
        if (createdAt == null) return 0;
        return Duration.between(createdAt, LocalDateTime.now()).toDays();
    }
}