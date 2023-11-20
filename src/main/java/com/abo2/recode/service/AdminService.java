package com.abo2.recode.service;

import com.abo2.recode.domain.post.PostRepository;
import com.abo2.recode.domain.qna.QnaRepository;
import com.abo2.recode.domain.quiz.QuizRepository;
import com.abo2.recode.domain.skill.Skill;
import com.abo2.recode.domain.skill.SkillRepository;
import com.abo2.recode.domain.skill.Study_skillRepository;
import com.abo2.recode.domain.studymember.Study_memberRepository;
import com.abo2.recode.domain.studyroom.AttendanceRepository;
import com.abo2.recode.domain.studyroom.StudyRoomRepository;
import com.abo2.recode.dto.admin.AdminReqDto;
import com.abo2.recode.dto.admin.AdminResDto;
import com.abo2.recode.dto.skill.SkillResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private SkillRepository skillRepository;

    private Study_skillRepository studySkillRepository;

    private Study_memberRepository studyMemberRepository;

    private PostRepository postRepository;

    private AttendanceRepository attendanceRepository;

    private QuizRepository quizRepository;

    private StudyRoomRepository studyRoomRepository;

    @Autowired
    public AdminService(SkillRepository skillRepository, Study_skillRepository studySkillRepository,
                        Study_memberRepository studyMemberRepository, PostRepository postRepository,
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
    public SkillResDto.AdminSkillAddResDto adminSkillAdd(String[] addskills){

        SkillResDto.AdminSkillAddResDto adminSkillAddResDto = new SkillResDto.AdminSkillAddResDto();
        ArrayList<String> skills = new ArrayList<>();

        for(String addskill : addskills){
            Skill skill = Skill.builder()
                    .skillName(addskill)
                    .build();
            skillRepository.save(skill);
            skills.add(addskill);
        }

        adminSkillAddResDto.setSkills(skills);

        return adminSkillAddResDto;

    }//adminSkillAdd()

    //관리자가 스터디룸 엔티티 삭제
    public AdminResDto.StudyDeleteResponseDto adminStudyRoomDelete(Long studyId) {

        // StudyRoom Entity 삭제 -> 연결 테이블 데이터도 전부 삭제 처리(CASCADE 옵션)
        studyRoomRepository.deleteById(studyId);

        return new AdminResDto.StudyDeleteResponseDto(studyId);
    } //adminStudyRoomDelete()

 /*   // 관리자 스터디 그룹 일반 멤버 스터디 그룹 장으로 승급
    public AdminResDto.MemberRoleResDto memberRoleChange(AdminReqDto.MemberRoleReqDto memberRoleReqDto) {
        //    - 현재 그룹장이 자신의 권한을 이전할 의사가 있는지 확인하기 위한 추가적인 인증 절차가 필요할 수 있습니다.
        //    - 한 번에 한 명의 멤버만이 그룹장이 될 수 있으므로, 권한 이전 시에 현재 그룹장은 자동으로 일반 멤버로 강등되는 로직이 필요합니다.

        *//*     {
            "code": 1,
                "msg": "사용자 권한이 성공적으로 변경되었습니다.",
                "data": {
            "userId": 42,
                    "newRole": "group_leader",
                    "updatedAt": "2023-11-06T12:00:00Z"
        }
        }*//*

    }*/

//    - 현재 그룹장이 자신의 권한을 이전할 의사가 있는지 확인하기 위한 추가적인 인증 절차가 필요할 수 있습니다.
//    - 한 번에 한 명의 멤버만이 그룹장이 될 수 있으므로, 권한 이전 시에 현재 그룹장은 자동으로 일반 멤버로 강등되는 로직이 필요합니다.

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
}//AdminService class
