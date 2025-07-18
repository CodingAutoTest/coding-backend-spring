package com.coding.backend.user.service;

import com.coding.backend.global.dto.UploadImageResponseDto;
import com.coding.backend.global.exception.ResourceNotFoundException;
import com.coding.backend.global.utils.EntityUtils;
import com.coding.backend.global.utils.FileUploader;
import com.coding.backend.user.dto.UserProblemProfileResponseDto;
import com.coding.backend.problem.entity.Problem;
import com.coding.backend.problemtag.entity.ProblemTag;
import com.coding.backend.tag.entity.Tag;
import com.coding.backend.user.dto.*;
import com.coding.backend.user.entity.User;
import com.coding.backend.user.repository.UserRepository;
import com.coding.backend.usersubmissionproblem.entity.UserSubmissionProblem;
import com.coding.backend.usersubmissionproblem.repository.UserSubmissionProblemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
    private final PasswordEncoder passwordEncoder;

//    @Qualifier("localUploader")
    private final FileUploader fileUploader;


    public UserProblemProfileResponseDto getUsernameAndProfileImageById(Integer id) {
        User user = EntityUtils.getByIdOrThrow(userRepository, id, "USER_NOT_FOUND");

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND"));

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
                .premiumStatus(user.getPremiumStatus())
                .tierCount(tierCount)
                .tagCount(tagCount)
                .solvedCountByDate(solvedCountByDate)
                .build();
    }

    public UserMyPageDto getUserMyPageInfoByName(String name) {
        Integer id = userRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"))
                .getId();
        return getUserMyPageInfo(id);
    }

    @Transactional
    public UploadImageResponseDto uploadImage(Integer userId,
                                              MultipartFile profileImg,
                                              MultipartFile bgImg) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));

        String profileUrl = null;
        String bgUrl = null;

        if (profileImg != null && !profileImg.isEmpty()) {
            profileUrl = fileUploader.upload(profileImg, "profile");
            user.setProfileImage(profileUrl);
        }
        if (bgImg      != null && !bgImg.     isEmpty()) {
            bgUrl  = fileUploader.upload(bgImg,      "background");
            user.setBackgroundImage(bgUrl);
        }

        userRepository.save(user);
        return UploadImageResponseDto.builder()
                .profileImageUrl(profileUrl)
                .backgroundImageUrl(bgUrl)
                .build();
    }

    @Transactional
    public void modifyProfile(Integer id, UserProfileModifyRequestDto dto) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            if (userRepository.existsByNameAndIdNot(dto.getName(), id)) {
                throw new IllegalArgumentException("사용자 이름이 중복되었습니다.");
            }
            u.setName(dto.getName());
        }

        if (dto.getProfileImageUrl() != null && !dto.getProfileImageUrl().isBlank()) {
            u.setProfileImage(dto.getProfileImageUrl());
        }
        if (dto.getBackgroundImageUrl() != null && !dto.getBackgroundImageUrl().isBlank()) {
            u.setBackgroundImage(dto.getBackgroundImageUrl());
        }
    }

    //해시 처리
    public void modifyPassword(Integer id, UserPasswordChangeRequestDto dto) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), u.getPw())) {
            throw new IllegalArgumentException("PASSWORD_MISMATCH");
        }
        u.setPw(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(u);
    }

//    public void modifyPassword(Integer id, UserPasswordChangeRequestDto dto) {
//        User u = userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));
//
//        /* 평문 비교: 추후 BCrypt 등으로 교체 */
//        if (!u.getPw().equals(dto.getCurrentPassword())) {
//            throw new IllegalArgumentException("PASSWORD_MISMATCH");
//        }
//        u.setPw(dto.getNewPassword());
//        userRepository.save(u);
//    }

    public void removeUser(Integer id) {
        userRepository.deleteById(id);
    }

    public void savePremium(Integer id, PremiumRequestDto dto) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));
        u.setPremiumStatus(dto.isSubscribe());
        userRepository.save(u);
    }
}
