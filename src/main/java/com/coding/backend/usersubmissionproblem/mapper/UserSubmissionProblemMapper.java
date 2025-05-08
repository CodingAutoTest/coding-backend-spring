package com.coding.backend.usersubmissionproblem.mapper;

import com.coding.backend.aifeedback.entity.AiFeedback;
import com.coding.backend.usersubmissionproblem.dto.SubmissionScore;
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
    UserSubmissionHistory toHistoryDto(UserSubmissionProblem problem);

    List<UserSubmissionHistory> toHistoryDto(List<UserSubmissionProblem> problems);

    @Named("mapLanguageName")
    default String mapLanguageName(com.coding.backend.language.entity.Language language) {
        return language != null ? language.getName() : null;
    }

    @Named("formatDate")
    default String formatDate(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }

    @Mapping(source = "aiFeedback", target = "feedback")
    @Mapping(target = "scores", expression = "java(mapToScore(userSubmissionProblem.getAiFeedback()))")
    @Mapping(target = "totalScore", expression = "java(calculateTotalScore(userSubmissionProblem.getAiFeedback()))")
    UserSubmissionProblemDto toDto(UserSubmissionProblem userSubmissionProblem);

    default String map(AiFeedback aiFeedback) {
        return aiFeedback != null ? aiFeedback.getFeedback() : null;
    }

    default SubmissionScore mapToScore(AiFeedback ai) {
        if (ai == null) return null;
        return SubmissionScore.builder()
                .accuracy(ai.getAccuracy())
                .efficiency(ai.getEfficiency())
                .readability(ai.getReadability())
                .testCoverage(ai.getTestCoverage())
                .build();
    }

    default Integer calculateTotalScore(AiFeedback ai) {
        if (ai == null) return 0;
        return ai.getAccuracy() +
                ai.getEfficiency() +
                ai.getReadability() +
                ai.getTestCoverage();
    }
}
