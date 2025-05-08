package com.coding.backend.user.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Builder
public class UserStreakStatsDto {
    private String name;
    private Map<LocalDate, Integer> solvedCountByDate;
}