package com.coding.backend.problem.service;

import com.coding.backend.global.exception.ResourceNotFoundException;
import com.coding.backend.problem.dto.ProblemDetailResponseDTO;
import com.coding.backend.problem.dto.ProblemDto;
import com.coding.backend.problem.entity.Problem;
import com.coding.backend.problem.repository.ProblemRepository;
import com.coding.backend.testcase.dto.TestcaseResponseDTO;
import com.coding.backend.testcase.repository.TestcaseRepository;
import com.coding.backend.usersubmissionproblem.repository.UserSubmissionProblemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final UserSubmissionProblemRepository userSubmissionProblemRepository;

    public ProblemDetailResponseDTO getProblemDetail(Integer id) {
        Problem problem = problemRepository.findWithTagsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 문제를 찾을 수 없습니다."));

        List<String> tagNames = problem.getProblemTags().stream()
                .map(pt -> pt.getTag().getName())
                .collect(Collectors.toList());

        return ProblemDetailResponseDTO.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .description(problem.getDescription())
                .inputConstraints(problem.getInputConstraints())
                .outputConstraints(problem.getOutputConstraints())
                .acceptanceRate(problem.getAcceptanceRate())
                .timeLimit(problem.getTimeLimit())
                .memoryLimit(problem.getMemoryLimit())
                .tags(tagNames)
                .build();
    }

    public Integer getDiffulty(Integer id) {
        Optional<Problem> optionalProblem = Optional.ofNullable(problemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("문제 객체를 찾을 수 없습니다.")));
        return optionalProblem.get().getDifficulty();
    }

    public Page<ProblemDto> getProblems(
            Integer tier,
            Long tagId,
            String search,
            Integer status,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        // 테스트용 userId 하드코딩
        int userId = 1;

        // 문제 필터링 쿼리 실행
        Page<Problem> problems = problemRepository.findFilteredProblems(
                status, tier, search, tagId, 1, pageable
        );

        return problems.map(problem -> convertToDto(problem, userId));
    }

    private ProblemDto convertToDto(Problem problem, Integer userId) {
        boolean submitted = userSubmissionProblemRepository.existsByUserIdAndProblemId(userId, problem.getId());

        return ProblemDto.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .difficulty(problem.getDifficulty())
                .acceptanceRate(problem.getAcceptanceRate())
                .status(submitted ? 1 : 0)  // TODO: 실제 유저 제출 상태를 확인하여 설정
                .userId(userId)  // TODO: 유저 정보 설정 (로그인된 사용자 ID를 기반으로 설정 가능)
                .build();
    }

}

