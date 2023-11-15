package com.abo2.recode.service;

import com.abo2.recode.domain.skill.Skill;
import com.abo2.recode.domain.skill.SkillRepository;
import com.abo2.recode.dto.skill.SkillResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminService {

    private SkillRepository skillRepository;

    @Autowired
    public AdminService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

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

}//AdminService class
