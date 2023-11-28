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

    @Transactional
    // 관리자 스터디 그룹 일반 멤버 스터디 그룹 장으로 승급
    public AdminResDto.MemberRoleResDto memberRoleChange(AdminReqDto.MemberRoleReqDto memberRoleReqDto) {
        //    - 현재 그룹장이 자신의 권한을 이전할 의사가 있는지 확인하기 위한 추가적인 인증 절차가 필요할 수 있습니다.
        //-> 알람 기능까지 구현해야 고려 가능한 기능이므로 일단 제외하고 구현

    /*    MemberRoleReqDto
            {
            "user_id" : 1,
            "role": "group_leader", //or "group_member"
            "study_id" : 1
        }
    }*/

        AdminResDto.MemberRoleResDto memberRoleResDto = AdminResDto.MemberRoleResDto.builder()
                .userId(memberRoleReqDto.getUser_id())
                .updatedAt(LocalDateTime.now())
                .build();

        Long study_id = studyRoomRepository.findCreated_byBystudy_id(memberRoleReqDto.getStudy_id());

        if((memberRoleReqDto.getRole().equals("group_leader")) && !(memberRoleReqDto.getUser_id()==study_id)){
            //조원을 조장으로 승격. studyRoom의 created_by를 체크해서 현재 유저가 조장이 맞는지도 체크해야함.

            //StudyRoom의 created_by를 업데이트 해야함.
            studyRoomRepository.memberRolePromote(memberRoleReqDto.getStudy_id(),memberRoleReqDto.getUser_id());

            memberRoleResDto.setNewRole("group_leader");
        }
        else if (memberRoleReqDto.getRole().equals("group_member")) { //조장을 조원으로 강등
            //StudyRoom의 created_by를 null로 업데이트 해야함.
            studyRoomRepository.memberRoleDemote(memberRoleReqDto.getStudy_id());

            memberRoleResDto.setNewRole("group_member");
        }


        //    - 한 번에 한 명의 멤버만이 그룹장이 될 수 있으므로, 권한 이전 시에 현재 그룹장은 자동으로 일반 멤버로 강등되는 로직이 필요합니다.

          /*      MemberRoleResDto
             {
            "code": 1,
                "msg": "사용자 권한이 성공적으로 변경되었습니다.",
                "data": {
            "userId": 42,
                    "newRole": "group_leader",
                    "updatedAt": "2023-11-06T12:00:00Z"
        }*/

        return memberRoleResDto;
    } //memberRoleChange()


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
    }//getSkills()
}//AdminService class
