package com.coding.backend.problem.service;

import com.coding.backend.global.exception.ResourceNotFoundException;
import com.coding.backend.global.utils.EntityUtils;
import com.coding.backend.problem.dto.ProblemDetailResponseDTO;
import com.coding.backend.problem.dto.ProblemDto;
import com.coding.backend.problem.entity.Problem;
import com.coding.backend.problem.mapper.ProblemMapper;
import com.coding.backend.problem.repository.ProblemRepository;
import com.coding.backend.usersubmissionproblem.repository.UserSubmissionProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final ProblemMapper problemMapper;
    private final UserSubmissionProblemRepository userSubmissionProblemRepository;

    public ProblemDetailResponseDTO getProblemDetail(Integer id) {
        Problem problem = problemRepository.findWithTagsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 문제를 찾을 수 없습니다."));

        return problemMapper.toDetailDto(problem);
    }

    public Integer getDiffulty(Integer id) {
        Problem problem = EntityUtils.getByIdOrThrow(
                problemRepository, id, "문제 객체를 찾을 수 없습니다.");

        return problem.getDifficulty();
    }

    public Page<ProblemDto> getProblems(
            String tier,
            Integer tagId,
            String search,
            String status,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        // 테스트용 userId
        int userId = 1;

        Page<Problem> problems = problemRepository.findFilteredProblems(
                status, tier, search, tagId, 1, pageable
        );

        return problems.map(problem -> convertToDto(problem, userId));
    }

    private ProblemDto convertToDto(Problem problem, Integer userId) {
        boolean submitted = userSubmissionProblemRepository.existsByUserIdAndProblemId(userId, problem.getId());
        boolean solvedAny = userSubmissionProblemRepository.existsByUserIdAndProblemIdAndStatus(userId, problem.getId(), true);

        int status;
        if (!submitted) {
            status = 0; // 미제출
        } else if (solvedAny) {
            status = 2; // 제출 + 성공 있음
        } else {
            status = 1; // 제출 + 성공 없음
        }

        return ProblemDto.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .difficulty(problem.getDifficulty())
                .acceptanceRate(problem.getAcceptanceRate())
                .status(status)
                .userId(userId)
                .build();
    }


}

