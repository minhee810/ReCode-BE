package com.abo2.recode.service;


import com.abo2.recode.domain.skill.Skill;
import com.abo2.recode.domain.skill.SkillRepository;
import com.abo2.recode.domain.studyroom.StudyRoomRepository;
import com.abo2.recode.dto.admin.AdminReqDto;
import com.abo2.recode.dto.admin.AdminResDto;
import com.abo2.recode.dto.skill.SkillReqDto;
import com.abo2.recode.dto.skill.SkillResDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final SkillRepository skillRepository;

    private final StudyRoomRepository studyRoomRepository;

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);


    // 관리자가 기술 스택 관리
    public SkillResDto.AdminSkillAddResDto adminSkillAdd(SkillReqDto.AdminSkillAddReqDto adminSkillAddReqDto) {

        Skill skill = Skill.builder()
                .skillName(adminSkillAddReqDto.getSkillName())
                .position(adminSkillAddReqDto.getPosition())
                .build();

        skillRepository.save(skill);

        SkillResDto.AdminSkillAddResDto adminSkillAddResDto = SkillResDto.AdminSkillAddResDto.builder()
                .position(skill.getPosition())
                .skillName(skill.getSkillName())
                .build();


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

    // 전체 스킬 불러오기
    public List<SkillResDto.AdminGetSkillResDto> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(skill -> SkillResDto.AdminGetSkillResDto.builder()
                        .skillName(skill.getSkillName())
                        .position(skill.getPosition())
                        .build())
                .collect(Collectors.toList());
    }

}