package com.coding.backend.user.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class UserLevelStatsDto {
    private String name;
    private Integer rating;
    private String profileImage;
    private String backgroundImage;
    private Integer solvedCount;
    private Map<Integer, Integer> tierCount;
}