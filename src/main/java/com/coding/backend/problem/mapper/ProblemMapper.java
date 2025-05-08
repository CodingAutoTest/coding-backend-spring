package com.coding.backend.problem.mapper;

import com.coding.backend.problem.dto.ProblemDetailResponseDTO;
import com.coding.backend.problem.entity.Problem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProblemMapper {

    @Mapping(target = "tags", expression = "java(mapTags(problem))")
    ProblemDetailResponseDTO toDetailDto(Problem problem);

    default List<String> mapTags(Problem problem) {
        if (problem.getProblemTags() == null) return List.of();
        return problem.getProblemTags().stream()
                .map(pt -> pt.getTag().getName())
                .toList();
    }
}
