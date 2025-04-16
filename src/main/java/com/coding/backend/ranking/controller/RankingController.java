package com.coding.backend.ranking.controller;

import com.coding.backend.ranking.dto.RankingDTO;
import com.coding.backend.ranking.service.RankingService;
import com.coding.backend.z.tools.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class RankingController {
    private final RankingService rankingService;

    @GetMapping("/rankings/{userId}")
    public ResponseEntity<ResultDto<?>> getRankingsWithMyInfo(@PathVariable Integer userId) {
        List<RankingDTO> top = rankingService.getTopUsers(); // top 100
        RankingDTO me = rankingService.getMyRanking(userId); // 내 정보 + 순위

        Map<String, Object> result = new HashMap<>();
        result.put("topRankings", top);
        result.put("myRanking", me);

        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "랭킹 조회 성공", result, "result"));
    }



}
