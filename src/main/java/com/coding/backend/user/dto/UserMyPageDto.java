package com.coding.backend.user.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMyPageDto {
    private String name;
    private Long rating;
    private Integer solvedCount;
    private String profileImage;
    private String backgroundImage;
    private Map<Integer, Integer> tierCount; // 티어 별 문제 푼 개수
    private Map<String, Integer> tagCount;   // 태그 별 문제 푼 개수
    private Map<LocalDate, Integer> solvedCountByDate; // 날짜 별 문제 푼 개수
}
