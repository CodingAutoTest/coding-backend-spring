package com.coding.backend.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값 필드는 응답에서 제외
public class ResultDto<T> {

    private Map<String, T> result;
    public static <T> ResultDto<T> of(String key, T data) {
        ResultDto<T> dto = new ResultDto<>();
        dto.result = Map.of(key, data);
        return dto;
    }

}