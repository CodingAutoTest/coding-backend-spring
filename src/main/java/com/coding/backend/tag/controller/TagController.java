package com.coding.backend.tag.controller;

import com.coding.backend.global.dto.ResultDto;
import com.coding.backend.tag.dto.TagDto;
import com.coding.backend.tag.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<ResultDto<List<TagDto>>> getTags() {
            List<TagDto> result = tagService.getAllTags();
            return ResponseEntity.ok(ResultDto.of("result", result));
    }
}


