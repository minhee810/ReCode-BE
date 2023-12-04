package com.abo2.recode.service;

import com.abo2.recode.domain.post.PostRepository;
import com.abo2.recode.domain.quiz.QuizRepository;
import com.abo2.recode.domain.skill.Skill;
import com.abo2.recode.domain.skill.SkillRepository;
import com.abo2.recode.domain.skill.StudySkillRepository;
import com.abo2.recode.domain.studymember.StudyMemberRepository;
import com.abo2.recode.domain.studyroom.AttendanceRepository;
import com.abo2.recode.domain.studyroom.StudyRoomRepository;
import com.abo2.recode.dto.admin.AdminReqDto;
import com.abo2.recode.dto.admin.AdminResDto;
import com.abo2.recode.dto.skill.SkillResDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private SkillRepository skillRepository;

    private StudySkillRepository studySkillRepository;

    private StudyMemberRepository studyMemberRepository;

    private PostRepository postRepository;

    private AttendanceRepository attendanceRepository;

    private QuizRepository quizRepository;

    private StudyRoomRepository studyRoomRepository;

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    public AdminService(SkillRepository skillRepository, StudySkillRepository studySkillRepository,
                        StudyMemberRepository studyMemberRepository, PostRepository postRepository,
                        AttendanceRepository attendanceRepository, QuizRepository quizRepository,
                        StudyRoomRepository studyRoomRepository) {
        this.skillRepository = skillRepository;
        this.studySkillRepository = studySkillRepository;
        this.studyMemberRepository = studyMemberRepository;
        this.postRepository = postRepository;
        this.attendanceRepository = attendanceRepository;
        this.quizRepository = quizRepository;
        this.studyRoomRepository = studyRoomRepository;
    }

    // 관리자가 기술 스택 관리
    public SkillResDto.AdminSkillAddResDto adminSkillAdd(String[] addskills) {

        SkillResDto.AdminSkillAddResDto adminSkillAddResDto = new SkillResDto.AdminSkillAddResDto();
        ArrayList<String> skills = new ArrayList<>();

        for (String addskill : addskills) {
            Skill skill = Skill.builder()
                    .skillName(addskill)
                    .build();
            skillRepository.save(skill);
            skills.add(addskill);
        }

        adminSkillAddResDto.setSkills(skills);

        return adminSkillAddResDto;

    }

    //관리자가 스터디룸 엔티티 삭제
    public AdminResDto.StudyDeleteResponseDto adminStudyRoomDelete(Long studyId) {

        // StudyRoom Entity 삭제 -> 연결 테이블 데이터도 전부 삭제 처리(CASCADE 옵션)
        studyRoomRepository.deleteById(studyId);

        return new AdminResDto.StudyDeleteResponseDto(studyId);
    }

    @Transactional
    // 관리자 스터디 그룹 일반 멤버 스터디 그룹 장으로 승급
    public AdminResDto.MemberRoleResDto memberRoleChange(AdminReqDto.MemberRoleReqDto memberRoleReqDto,
                                                         Long studyId, Long userId) {
        //    - 현재 그룹장이 자신의 권한을 이전할 의사가 있는지 확인하기 위한 추가적인 인증 절차가 필요할 수 있습니다.

        AdminResDto.MemberRoleResDto memberRoleResDto = AdminResDto.MemberRoleResDto.builder()
                .userId(userId)
                .updatedAt(LocalDateTime.now())
                .build();

        try {
            Long createdBy = studyRoomRepository.findcreatedByBystudyId(studyId);
            logger.info(createdBy.toString());

            if ((memberRoleReqDto.getRole().equals("group_leader"))
                    && !(userId == createdBy)) {
                //조원을 조장으로 승격. studyRoom의 createdBy를 체크해서 현재 유저가 조장이 맞는지도 체크해야함.

                //StudyRoom의 createdBy를 업데이트 해야함.
                studyRoomRepository.memberRolePromote(studyId, userId);

                memberRoleResDto.setNewRole("group_leader");
            } else if (memberRoleReqDto.getRole().equals("group_member")) { //조장을 조원으로 강등
                //StudyRoom의 createdBy를 null로 업데이트 해야함.
                studyRoomRepository.memberRoleDemote(studyId);

                memberRoleResDto.setNewRole("group_member");
            }


        } catch (NullPointerException e) {
            //조장이 공석인 상황 -> 무조건 해당 유저를 조장으로 승격
            studyRoomRepository.memberRolePromote(studyId, userId);
            memberRoleResDto.setNewRole("group_leader");
        }

        return memberRoleResDto;
    }

    // 스킬 목록 조회
    public SkillResDto.AdminSkillAddResDto getSkills() {
        List<Skill> skillList = skillRepository.findAll();
        ArrayList<String> skillNames = skillList.stream()
                .map(Skill::getSkillName)
                .collect(Collectors.toCollection(ArrayList::new));

        SkillResDto.AdminSkillAddResDto response = new SkillResDto.AdminSkillAddResDto();
        response.setSkills(skillNames);
        return response;
    }

}
