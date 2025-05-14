package com.coding.backend.ranking.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingDTO {
    private Integer userId;
    private Integer rank;
    private String name;
    private String profileImage;
    private String tier;
    private Long rating;
    private Integer solvedCount;
    private Long marathonDays;

}
