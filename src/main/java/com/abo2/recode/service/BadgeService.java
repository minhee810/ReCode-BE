package com.abo2.recode.service;

import com.abo2.recode.domain.badge.Badge;
import com.abo2.recode.domain.badge.BadgeRepository;
import com.abo2.recode.domain.badge.UserBadge;
import com.abo2.recode.domain.badge.UserBadgeRepository;
import com.abo2.recode.domain.estimate.Estimate;
import com.abo2.recode.domain.estimate.EstimateRepository;
import com.abo2.recode.domain.estimate.StudyMemberEstimate;
import com.abo2.recode.domain.estimate.StudyMemberEstimateRepository;
import com.abo2.recode.domain.studymember.StudyMember;
import com.abo2.recode.domain.studymember.StudyMemberRepository;
import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.studyroom.StudyRoomRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.badge.BadgeReqDto;
import com.abo2.recode.dto.badge.BadgeRespDto;
import com.abo2.recode.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BadgeService {

    private final UserRepository userRepository;
    private final StudyRoomRepository studyRoomRepository;
    private final StudyMemberEstimateRepository studyMemberEstimateRepository;
    private final EstimateRepository estimateRepository;
    private final BadgeRepository badgeRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final StudyMemberRepository studyMemberRepository;

    @Transactional
    public BadgeRespDto.EstimateRespDto estimate(Long loginUserId, Long studyId, Long userId, BadgeReqDto.EstimateReqDto estimateReqDto) {
        // 1. user 아이디 조회
        User user = userRepository.findById(estimateReqDto.getUserId()).orElseThrow(() -> new CustomApiException("존재하지 않는 사용자입니다."));

        // 2. StudyRoom 아이디 조회
        StudyRoom studyRoom = studyRoomRepository.findById(estimateReqDto.getStudyId()).orElseThrow(() -> new CustomApiException("스터디 룸이 존재하지 않습니다."));

        // 3. 자신이 자신을 평가하는 지 확인
        if (loginUserId.equals(userId)) {
            throw new CustomApiException("자신을 평가할 수 없습니다.");
        }

        // 4. 평가 데이터 처리
        StudyMemberEstimate studyMemberEstimate = processEstimate(studyId, userId, estimateReqDto);
        studyMemberEstimateRepository.save(studyMemberEstimate);

        Estimate estimate = processEstimate(studyMemberEstimate, estimateReqDto);
        estimateRepository.save(estimate);

        // 5. 뱃지 업데이트
        Badge badge = determineBadge(estimateReqDto.getPoint());
        assignBadgeToMember(user.getId(), badge);

        return createEstimateRespDto(estimate);
    }

    private StudyMemberEstimate processEstimate(Long studyId, Long userId, BadgeReqDto.EstimateReqDto estimateReqDto) {
        // 평가된 점수가 있는지 확인
        StudyMemberEstimate studyMemberEstimate = studyMemberEstimateRepository.findByUserIdAndStudyRoomId(userId, studyId)
                .orElseGet(() -> {
                    StudyMemberEstimate newEstimate = new StudyMemberEstimate();
                    newEstimate.setTotalPoint(0);
                    return newEstimate;
                });

        // StudyRoom과 StudyMember 설정
        StudyRoom studyRoom = studyRoomRepository.findById(studyId).orElseThrow(()
                -> new IllegalArgumentException("해당 스터디가 존재하지 않습니다."));
        User user = userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        studyMemberEstimate.setStudyRoom(studyRoom);
        studyMemberEstimate.setUser(user);

        // 총 점수 업데이트
        int totalPoint = (studyMemberEstimate.getTotalPoint() != null) ? studyMemberEstimate.getTotalPoint() : 0;
        studyMemberEstimate.setTotalPoint(totalPoint + estimateReqDto.getPoint());
        studyMemberEstimate.setCreatedAt(LocalDateTime.now());
        studyMemberEstimateRepository.save(studyMemberEstimate);

        return studyMemberEstimate;
    }

    private Estimate processEstimate(StudyMemberEstimate studyMemberEstimate, BadgeReqDto.EstimateReqDto estimateReqDto) {
        // Estimate 객체 생성
        Estimate estimate = new Estimate();
        estimate.setPoint(estimateReqDto.getPoint());

        // StudyMemberEstimate 설정
        estimate.setStudyMemberEstimate(studyMemberEstimate);

        // Badge 설정
        Badge badge = badgeRepository.findById(1L).orElseThrow(()
                -> new IllegalArgumentException("해당 뱃지가 유효하지 않습니다."));
        estimate.setBadge(badge);

        return estimate;
    }

    private BadgeRespDto.EstimateRespDto createEstimateRespDto(Estimate estimate) {

        return new BadgeRespDto.EstimateRespDto(estimate);
    }

    private int calculateTotalPointsForMember(StudyMember member) {
        // StudyMemberEstimate에서 해당 멤버의 총점을 조회
        Optional<StudyMemberEstimate> estimateOpt = studyMemberEstimateRepository.findByUserIdAndStudyRoomId(member.getUser().getId(), member.getStudyRoom().getId());

        if (estimateOpt.isPresent()) {
            return estimateOpt.get().getTotalPoint();
        } else {
            // 평가 점수가 없는 경우, 0점을 반환
            return 0;
        }
    }

    // 점수에 따라 뱃지를 결정하는 메서드
    private Badge determineBadge(int point) {
        // 점수가 100점 이상이면 badge_id가 3인 뱃지를, 50점이상 100점 미만이면 badge_id 가 2인 뱃지를 그 외에는 badge_id 가 1인 뱃지를 부여
        Long badgeId;
        if (point >= 100) {
            badgeId = 3L;
        } else if (point >= 50 && point < 100) {
            badgeId = 2L;
        } else {
            badgeId = 1L;
        }
        return badgeRepository.findById(badgeId).orElseThrow(() -> new IllegalArgumentException("잘못된 뱃지 요청입니다."));
    }

    private void assignBadgeToMember(Long userId, Badge badge) {
        // Member 조회
        List<StudyMember> members = studyMemberRepository.findByUserId(userId);
        if (members.isEmpty()) {
            throw new IllegalArgumentException("해당 사용자가 존재하지 않습니다.");
        }
        // 반환된 리스트에서 첫 번째 값만 조회
        StudyMember member = members.get(0);

        // 사용자에게 뱃지 부여
        UserBadge userBadge = new UserBadge();
        userBadge.setUser(member.getUser());
        userBadge.setBadge(badge);
        userBadge.setUser(member.getUser());
        userBadge.setPoint(calculateTotalPointsForMember(member));
        userBadge.setCreatedAt(LocalDateTime.now());
        userBadgeRepository.save(userBadge);
    }

    public BadgeRespDto.GetBadgeRespDto getBadge(Long userId) {
        // 뱃지 정보를 가져오려는 대상 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("해당 사용자가 존재하지 않습니다."));

        // 뱃지 정보 가져오기
        UserBadge getBadge = userBadgeRepository.findByUserId(userId).orElseThrow(() -> new CustomApiException("뱃지 정보가 없습니다."));

        return new BadgeRespDto.GetBadgeRespDto(getBadge);
    }
}
