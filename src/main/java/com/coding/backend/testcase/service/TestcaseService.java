package com.coding.backend.testcase.service;

import com.coding.backend.testcase.dto.TestcaseResponseDTO;
import com.coding.backend.testcase.repository.TestcaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TestcaseService {

    private final TestcaseRepository testcaseRepository;
    public List<TestcaseResponseDTO> getTestcasesByProblemId(Integer problemId) {
        return testcaseRepository.findTop3ByProblemIdOrderByIdAsc(problemId)
                .stream()
                .map(TestcaseResponseDTO::from)
                .collect(Collectors.toList());
    }
}
