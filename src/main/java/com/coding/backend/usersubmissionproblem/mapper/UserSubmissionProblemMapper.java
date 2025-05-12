package com.coding.backend.usersubmissionproblem.mapper;

import com.coding.backend.aifeedback.dto.AiFeedbackDto;
import com.coding.backend.aifeedback.entity.AiFeedback;
import com.coding.backend.usersubmissionproblem.dto.UserSubmissionHistory;
import com.coding.backend.usersubmissionproblem.dto.UserSubmissionProblemDto;
import com.coding.backend.usersubmissionproblem.entity.UserSubmissionProblem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserSubmissionProblemMapper {
    @Mapping(source = "language", target = "language", qualifiedByName = "mapLanguageName")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "formatDate")
    UserSubmissionHistory toHistoryDto(UserSubmissionProblem usp);


    @Named("mapLanguageName")
    default String mapLanguageName(com.coding.backend.language.entity.Language language) {
        return language != null ? language.getName() : null;
    }

    @Named("formatDate")
    default String formatDate(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }

    default String map(AiFeedback aiFeedback) {
        return aiFeedback != null ? aiFeedback.getFeedback() : null;
    }

    List<UserSubmissionHistory> toHistoryDto(List<UserSubmissionProblem> problems);

    @Mapping(target = "aiFeedbackDto", expression = "java(toFeedbackDto(usp.getAiFeedback()))")
    UserSubmissionProblemDto toDto(UserSubmissionProblem usp);

    default AiFeedbackDto toFeedbackDto(AiFeedback ai) {
        if (ai == null) return null;
        return AiFeedbackDto.builder()
                .accuracy(ai.getAccuracy())
                .efficiency(ai.getEfficiency())
                .readability(ai.getReadability())
                .test_coverage(ai.getTestCoverage())
                .feedback(ai.getFeedback())
                .totalScore(
                        ai.getAccuracy() + ai.getEfficiency() + ai.getReadability() + ai.getTestCoverage()
                )
                .build();
    }


}
