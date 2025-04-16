package com.coding.backend.z.tools;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값 필드는 응답에서 제외
public class ResultDto<T> {

    private int status;
    private String message;
    private Map<String, T> result;


    // Ex) return ResponseEntity.ok(ResultDto.of(HttpStatus.OK, "실행 성공"));
    // Ex) return ResponseEntity
    //                    .status(HttpStatus.NOT_FOUND)
    //                    .body(ResultDto.of(HttpStatus.NOT_FOUND, "Error", result, "result"));
    public static <T> ResultDto<T> of(HttpStatus status, String message) {
        ResultDto<T> dto = new ResultDto<>();
        dto.status = status.value();
        dto.message = message;
        return dto;
    }

    // Ex) return ResultDTO.of(HttpStatus.OK, "문제 조회 성공", problemDto, "problem");
    public static <T> ResultDto<T> of(HttpStatus status, String message, T data, String key) {
        ResultDto<T> dto = new ResultDto<>();
        dto.status = status.value();
        dto.message = message;
        dto.result = Map.of(key, data);
        return dto;
    }
}