package com.coding.backend.tag.service;

import com.coding.backend.tag.dto.TagDto;
import com.coding.backend.tag.entity.Tag;
import com.coding.backend.tag.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagDto> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .map(tag -> new TagDto(tag.getId(), tag.getName()))
                .collect(Collectors.toList());
    }
}

