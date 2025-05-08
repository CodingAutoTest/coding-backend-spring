package com.coding.backend.user.service;

import com.coding.backend.global.exception.ResourceNotFoundException;
import com.coding.backend.global.utils.EntityUtils;
import com.coding.backend.user.dto.UserProblemProfileResponseDto;
import com.coding.backend.problem.entity.Problem;
import com.coding.backend.problemtag.entity.ProblemTag;
import com.coding.backend.tag.entity.Tag;
import com.coding.backend.user.dto.*;
import com.coding.backend.user.entity.User;
import com.coding.backend.user.repository.UserRepository;
import com.coding.backend.usersubmissionproblem.entity.UserSubmissionProblem;
import com.coding.backend.usersubmissionproblem.repository.UserSubmissionProblemRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSubmissionProblemRepository userSubmissionProblemRepository;

    public UserProblemProfileResponseDto getUsernameAndProfileImageById(Integer id) {
        User user = EntityUtils.getByIdOrThrow(userRepository, id, "유저가 없습니다.");

        UserProblemProfileResponseDto dto = new UserProblemProfileResponseDto();
        dto.setName(user.getName());
        dto.setProfileImage(user.getProfileImage());

        return dto;
    }

    public Map<Integer, Integer> getTierStats(Integer userId) {
        return extractStats(userId).getTierCount();
    }

    public Map<String, Integer> getTagStats(Integer userId) {
        return extractStats(userId).getTagCount();
    }

    public Map<LocalDate, Integer> getStreakStats(Integer userId) {
        return extractStats(userId).getSolvedCountByDate();
    }

    public UserMyPageDto getUserMyPageInfo(Integer userId) {
        return extractStats(userId);
    }

    private UserMyPageDto extractStats(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));

        List<UserSubmissionProblem> submissions = userSubmissionProblemRepository.findByUserIdAndStatusTrue(userId);

        Set<Integer> uniqueProblemIds = new HashSet<>();
        Map<Integer, Integer> tierCount = new HashMap<>();
        Map<String, Integer> tagCount = new HashMap<>();
        Map<LocalDate, Integer> solvedCountByDate = new HashMap<>();

        for (UserSubmissionProblem submission : submissions) {
            Problem problem = submission.getProblem();
            Integer problemId = problem.getId();

            if (uniqueProblemIds.add(problemId)) {
                tierCount.merge(problem.getDifficulty(), 1, Integer::sum);

                if (problem.getProblemTags() != null) {
                    for (ProblemTag pt : problem.getProblemTags()) {
                        Tag tag = pt.getTag();
                        if (tag != null) {
                            tagCount.merge(tag.getName(), 1, Integer::sum);
                        }
                    }
                }
            }

            LocalDate solvedDate = submission.getCreatedAt().toLocalDate();
            solvedCountByDate.merge(solvedDate, 1, Integer::sum);
        }

        return UserMyPageDto.builder()
                .name(user.getName())
                .rating(user.getRating())
                .solvedCount(uniqueProblemIds.size())
                .profileImage(user.getProfileImage())
                .backgroundImage(user.getBackgroundImage())
                .tierCount(tierCount)
                .tagCount(tagCount)
                .solvedCountByDate(solvedCountByDate)
                .build();
    }
}
