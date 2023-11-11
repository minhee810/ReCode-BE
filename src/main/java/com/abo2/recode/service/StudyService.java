package com.abo2.recode.service;

import com.abo2.recode.domain.skill.Skill;
import com.abo2.recode.domain.skill.SkillRepository;
import com.abo2.recode.domain.skill.Study_skill;
import com.abo2.recode.domain.skill.Study_skillRepository;
import com.abo2.recode.domain.studyroom.StudyRepository;
import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.dto.study.StudyReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@Service
public class StudyService {

    private StudyRepository studyRepository;

    private Study_skillRepository studySkillRepository;

    private SkillRepository skillRepository;

    @Autowired
    public StudyService(StudyRepository studyRepository, Study_skillRepository studySkillRepository, SkillRepository skillRepository) {
        this.studyRepository = studyRepository;
        this.studySkillRepository = studySkillRepository;
        this.skillRepository = skillRepository;
    }

    public void createRoom(StudyReqDto.StudyCreateReqDto studyCreateReqDto){

        System.out.println("Service createRoom()");

        //1. 넘겨 받은 studyReqDto에서 정보 가져오기

        String study_name = studyCreateReqDto.getStudy_name();
        String title = studyCreateReqDto.getTitle();
        String description = studyCreateReqDto.getDescription();
        LocalDate start_date = studyCreateReqDto.getStart_date();
        LocalDate end_date = studyCreateReqDto.getEnd_date();
        Integer current_num = 1; //스터디 그룹 현재 인원은 기본 1명으로 설정
        Integer max_num = studyCreateReqDto.getMax_num();
        Long user_id = studyCreateReqDto.getUser_id();

        //1-1. start_time,end_time String -> LocalDateTime
        LocalDateTime startDateTime = convertToDateTime(studyCreateReqDto.getStart_time());
        LocalDateTime endDateTime = convertToDateTime(studyCreateReqDto.getEnd_time());

        //2. DB에 전송할 studyRoom Entity 선언, studyRoom Entity에 데이터 집어 넣기, DB에 Insert
        StudyRoom studyRoom = StudyRoom.builder()
                .study_name(study_name)
                .title(title)
                .description(description)
                .start_time(startDateTime)
                .end_time(endDateTime)
                .start_date(start_date)
                .end_date(end_date)
                .current_num(current_num)
                .max_num(max_num)
                .created_By(user_id)
                .build();

        studyRepository.save(studyRoom);

        //3.study_skill 테이블에 skill 삽입
        //3-1 Study_skill Entity 선언, Study_skill Entity에 데이터 집어 넣기, DB에 Insert
        // expertise
        for (String skillName : studyCreateReqDto.getSkills()) {
            Skill skill = skillRepository.findBySkillName(skillName); // 스킬 이름으로 Skill 엔티티 검색

            Study_skill studySkill = Study_skill.builder()
                    .studyRoom(studyRoom)
                    .skill(skill)
                    .build();
            studySkillRepository.save(studySkill);
        }
        System.out.println("Service createRoom() save !!!!!!");

    } //createRoom()

    // 문자열을 LocalDateTime 객체로 변환
    private LocalDateTime convertToDateTime(String dateTimeStr) {
        String[] parts = dateTimeStr.split(" ");
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(parts[0].toUpperCase());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sameWeekDay = now.with(TemporalAdjusters.nextOrSame(dayOfWeek));
        LocalDateTime time = LocalDateTime
                .parse(sameWeekDay.format(DateTimeFormatter.ISO_LOCAL_DATE)
                + "T" + parts[1], DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return time;
    }//convertToDateTime()


}//class StudyService
