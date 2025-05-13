package com.coding.backend.usersubmissionproblem.service;

import com.coding.backend.global.utils.EntityUtils;
import com.coding.backend.usersubmissionproblem.dto.UserSubmissionHistory;
import com.coding.backend.usersubmissionproblem.mapper.UserSubmissionProblemMapper;
import com.coding.backend.usersubmissionproblem.dto.UserSubmissionProblemDto;
import com.coding.backend.usersubmissionproblem.entity.UserSubmissionProblem;
import com.coding.backend.usersubmissionproblem.repository.UserSubmissionProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserSubmissionProblemService {

    private final UserSubmissionProblemRepository userSubmissionProblemRepository;
    private final UserSubmissionProblemMapper userSubmissionProblemMapper;

    public UserSubmissionProblemDto findSubmissionDetails(Integer id) {
        UserSubmissionProblem userSubmissionProblem = EntityUtils.getByIdOrThrow(
                userSubmissionProblemRepository, id, "제출 내역 없음");
        return userSubmissionProblemMapper.toDto(userSubmissionProblem);
    }

    public List<UserSubmissionHistory> findSubmissionHistoryList(Integer userId, Integer submissionId) {
        List<UserSubmissionProblem> list = EntityUtils.getListOrEmpty(
                userSubmissionProblemRepository.findAllByUserIdAndProblemId(userId, submissionId));

        return userSubmissionProblemMapper.toHistoryDto(list);
    }

    public String findSubmissionCode(Integer id) {
        UserSubmissionProblem userSubmissionProblem = EntityUtils.getByIdOrThrow(
                userSubmissionProblemRepository, id, "제출 내역 코드 없음");
        return userSubmissionProblem.getSubmissionCode();
    }



}
