package com.coding.backend.ranking.controller;

import com.coding.backend.ranking.dto.RankingDTO;
import com.coding.backend.ranking.service.RankingService;
import com.coding.backend.z.tools.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor

public class RankingController {
    private final RankingService rankingService;
    @GetMapping("/ranking")
    public ResponseEntity<ResultDto<?>> getRanking(
            @RequestParam(defaultValue = "rating") String sort,
            @RequestParam(defaultValue = "desc") String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String name
    ) {
        Map<String, Object> result = rankingService.getRankingWithPageable(sort, order, page, size, name);
        return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "랭킹 조회 성공", result, "result"));
    }

@GetMapping("/me/ranking")
public ResponseEntity<ResultDto<?>> getMyRanking(@RequestParam Integer userid) {
    RankingDTO myRanking = rankingService.getMyRanking(userid);
    return ResponseEntity.ok(ResultDto.of(
            HttpStatus.OK,
            "내 랭킹 조회 성공",
            myRanking,
            "myRanking"
    ));
}


}
