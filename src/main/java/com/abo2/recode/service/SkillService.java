package com.abo2.recode.service;

import com.abo2.recode.domain.skill.Skill;
import com.abo2.recode.domain.skill.SkillRepository;
import com.abo2.recode.dto.skill.SkillResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SkillService {

    private final SkillRepository skillRepository;


    @Transactional
    public List<SkillResDto.SkillNameRespDto> skillNameByPosition(String position) {
        List<Skill> skills = skillRepository.findByPosition(position);

        // 2. 스킬 이름을 추출하여 getSkillNameRespDto 리스트 생성
        List<SkillResDto.SkillNameRespDto> skillNameRespDtoList = new ArrayList<>();

        for (Skill skill : skills) {
            SkillResDto.SkillNameRespDto skillNameRespDto = new SkillResDto.SkillNameRespDto(skill.getSkillName());
            skillNameRespDtoList.add(skillNameRespDto);
        }
        return skillNameRespDtoList;

    }
}
