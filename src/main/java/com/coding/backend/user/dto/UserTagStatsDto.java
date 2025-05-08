package com.coding.backend.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class UserTagStatsDto {
    private String name;
    private Map<String, Integer> tagCount;
}