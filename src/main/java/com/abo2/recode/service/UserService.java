package com.abo2.recode.service;

import com.abo2.recode.domain.skill.StudySkill;
import com.abo2.recode.domain.skill.StudySkillRepository;
import com.abo2.recode.domain.studymember.StudyMember;
import com.abo2.recode.domain.studymember.StudyMemberRepository;
import com.abo2.recode.domain.studyroom.StudyRoomRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.study.StudyResDto;
import com.abo2.recode.dto.user.UserReqDto;
import com.abo2.recode.dto.user.UserRespDto;
import com.abo2.recode.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final StudyMemberRepository studyMemberRepository;
    private final StudySkillRepository studySkillRepository;
    private final StudyRoomRepository studyRoomRepository;

    @Transactional
    public UserRespDto.JoinRespDto 회원가입(UserReqDto.JoinReqDto joinReqDto) {
        // 1. 동일 유저네임 존재 검사
        Optional<User> userOP = userRepository.findByUsername(joinReqDto.getUsername());
        if (userOP.isPresent()) {
            throw new CustomApiException("동일한 username이 존재합니다.");
        }

        // 2. 동일한 이메일 존재 검사
        Optional<User> userEM = userRepository.findByEmail(joinReqDto.getEmail());
        if (userEM.isPresent()) {
            throw new CustomApiException("동일한 email 로 가입된 계정이 존재합니다.");
        }

        // 2. 패스워드 인코딩 + 회원가입
        User userPS = userRepository.save(joinReqDto.toEntity(passwordEncoder));

        // 3. dto 응답
        return new UserRespDto.JoinRespDto(userPS);
    }

    @Transactional
    public UserRespDto.JoinRespDto admin_join(UserReqDto.JoinAdminReqDto joinAdminReqDto) {
        // 1. 동일 유저네임 존재 검사
        Optional<User> userOP = userRepository.findByUsername(joinAdminReqDto.getUsername());
        if (userOP.isPresent()) {
            throw new CustomApiException("동일한 username이 존재합니다.");
        }

        // 2. 패스워드 인코딩 + 회원가입
        User userPS = userRepository.save(joinAdminReqDto.toEntity(passwordEncoder));

        // 3. dto 응답
        return new UserRespDto.JoinRespDto(userPS);
    }

    @Transactional
    public boolean checkUsernameDuplicate(String username){
        // 1. 회원가입 시 username 중복확인
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public boolean checkEmailDuplicate(String email){
        // 1. 회원가입 시 email 중복확인
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public UserRespDto.FindUsernameRespDto findUsername(UserReqDto.FindUsernameReqDto findUsernameReqDto) {
        // 1. 이메일로 user 정보 조회
        User userPS = userRepository.findByEmail(findUsernameReqDto.getEmail()).orElseThrow(()
                -> new CustomApiException("존재하지 않는 사용자입니다."));

        // 2. dto 응답
        return new UserRespDto.FindUsernameRespDto(userPS.getUsername());

    }

    @Transactional
    public UserRespDto.UpdateUserRespDto updateUser(Long userId, UserReqDto.UpdateUserReqDto updateUserReqDto){
        // 1. user 아이디 조회
        User userPS = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("존재하지 않는 사용자입니다."));

        // 2. update()로 객체에 변경사항 반영
        userPS.updateUser(updateUserReqDto.getNickname(), updateUserReqDto.getEmail());

        // 3. dto 응답
        return new UserRespDto.UpdateUserRespDto(userPS);
    }

    @Transactional
    public UserRespDto.EssayRespDto writeEssay(Long userId, UserReqDto.WriteEssayReqDto writeEssayReqDto){
        // 1. user 아이디 조회
        User userPS = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("존재하지 않는 사용자입니다."));

        // 2. 객체에 변경 사항 반영
        userPS.writeEssay(writeEssayReqDto.getEssay());

        // 3. dto 응답
        return new UserRespDto.EssayRespDto(userPS);
    }

    @Transactional
    public UserRespDto.EssayRespDto getEssay(Long userId){
        // 1. user 아이디 조회
        User userPS = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("존재하지 않는 사용자입니다."));

        // 2. dto 응답
        return new UserRespDto.EssayRespDto(userPS);
    }

    @Transactional
    public void withdrawUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("존재하지 않는 사용자입니다."));

        // 가입 중인 스터디룸 중에서 스터디장의 권한으로 있는 스터디 룸이 있는지 확인
        boolean isMasterOfAnyRoom = studyRoomRepository.existsByMaster(user);

        if (isMasterOfAnyRoom) {
            throw new CustomApiException("스터디장의 권한을 갖고 계신 스터디룸이 존재합니다. 권한을 양도한 후에 탈퇴를 진행해주시기 바랍니다.");
        }

        // 스터디장의 권한으로 있는 스터디 룸이 없다면 스터디룸에 작성한 글, 퀴즈, Qna 에서 작성한 글을 제외한 정보 삭제
        userRepository.dissociateStudyMember(userId);
        userRepository.dissociatePosts(userId);
        userRepository.dissociatePostReply(userId);
        userRepository.dissociateQnas(userId);
        userRepository.dissociateQuiz(userId);
        userRepository.deleteUsersAttendance(userId);
        userRepository.deleteWithoutRelatedInfo(userId);

    }

    @Transactional
    public UserRespDto.getUserInfoDto getUserInfo(Long userId){
        // 1. user 아이디 조회
        User userPS = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("존재하지 않는 사용자입니다."));

        // 2. dto 응답
        return new UserRespDto.getUserInfoDto(userPS);
    }

    public List<StudyResDto.MyStudyRespDto> myStudy(Long userId) {
        List<StudyMember> studyMembers = studyMemberRepository.findByUserId(userId);
        List<StudyResDto.MyStudyRespDto> myStudyRespDtos = new ArrayList<>();

        for (StudyMember studyMember : studyMembers) {
            List<StudySkill> skills = studySkillRepository.findByStudyRoomId(studyMember.getStudyRoom().getId());
            StudyResDto.MyStudyRespDto myStudyRespDto = new StudyResDto.MyStudyRespDto(studyMember, skills);
            myStudyRespDtos.add(myStudyRespDto);
        }

        return myStudyRespDtos;
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 사용자를 찾을 수 없습니다."));
    }

    @Transactional
    public UserRespDto.changePasswordRespDto changePassword(Long userId, UserReqDto.ChangePasswordReqDto changePasswordReqDto) {
        // 1. 사용자 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 사용자입니다."));

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(changePasswordReqDto.getPassword());

        // 3. 암호화된 비밀번호로 변경
        user.changePassword(encodedPassword);

        // 4. 변경된 사용자 정보 저장
        userRepository.save(user);

        return new UserRespDto.changePasswordRespDto(user);
    }

    @Transactional
    public UserRespDto.changePasswordRespDto changePasswordWithToken(String emailCheckToken, UserReqDto.ChangePasswordReqDto changePasswordReqDto) {
        // 1. 토큰 유효성 검증 및 사용자 찾기
        User user = userRepository.findByEmailCheckToken(emailCheckToken)
                .orElseThrow(() -> new CustomApiException("유효하지 않은 토큰입니다."));

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(changePasswordReqDto.getPassword());

        // 3. 암호화된 비밀번호로 변경
        user.changePassword(encodedPassword);

        // 4. 변경된 사용자 정보 저장
        userRepository.save(user);

        return new UserRespDto.changePasswordRespDto(user);
    }

}
