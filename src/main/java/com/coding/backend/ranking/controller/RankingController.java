package com.coding.backend.ranking.controller;

import com.coding.backend.global.utils.SecurityUtil;
import com.coding.backend.ranking.dto.RankingDTO;
import com.coding.backend.ranking.service.RankingService;
import com.coding.backend.global.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rankings")
@RequiredArgsConstructor

public class RankingController {
    private final RankingService rankingService;
    @GetMapping
    public ResponseEntity<ResultDto<?>> getRanking(
            @RequestParam(defaultValue = "rating") String sort,
            @RequestParam(defaultValue = "desc") String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String name
    ) {
        Map<String, Object> result = rankingService.getRankingWithPageable(sort, order, page, size, name);
        return ResponseEntity.ok(ResultDto.of("result", result));
    }
    @GetMapping("/me")
    public ResponseEntity<ResultDto<?>> getMyRanking() {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) {
            // ⚠ null 넘기면 Map.of에서 NPE 발생하므로, 따로 처리
            return ResponseEntity.ok(new ResultDto<>());
        }

        RankingDTO result = rankingService.getMyRanking(userId);
        return ResponseEntity.ok(ResultDto.of("result", result));
    }
}