package com.coding.backend.problem.service;

import com.coding.backend.global.exception.ResourceNotFoundException;
import com.coding.backend.problem.dto.ProblemDetailResponseDTO;
import com.coding.backend.problem.entity.Problem;
import com.coding.backend.problem.repository.ProblemRepository;
import com.coding.backend.testcase.dto.TestcaseResponseDTO;
import com.coding.backend.testcase.repository.TestcaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProblemService {

    private final ProblemRepository problemRepository;


    public ProblemDetailResponseDTO getProblemDetail(Integer id) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 문제를 찾을 수 없습니다."));
        return ProblemDetailResponseDTO.from(problem);
    }

}
