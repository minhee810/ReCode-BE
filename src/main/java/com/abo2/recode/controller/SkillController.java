package com.abo2.recode.controller;

import com.abo2.recode.domain.skill.SkillRepository;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.skill.SkillResDto;
import com.abo2.recode.service.NoticeService;
import com.abo2.recode.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class SkillController {

    private final SkillService skillService;

    private final SkillRepository skillRepository;

    // POSITION 에 따라서 skillName을 반환하는 메서드

    @GetMapping("/skills/get-skillName")
    public ResponseEntity<?> getSkillPosition(@RequestParam String position){

        // 1. 받아온 position 으로 skillName List 로 저장
        List<SkillResDto.SkillNameRespDto> skills = skillService.skillNameByPosition(position);

        return new ResponseEntity<>(new ResponseDto<>(1, "선택된 포지션으로 스킬 이름 불러오기 성공", skills), HttpStatus.OK);
    }


//    // 중복 제거된 포지션 목록을 반환하는 엔드포인트
//    @GetMapping("/skills/get-positions")
//    public List<String> getDistinctPositions() {
//        return skillRepository.findDistinctPositions();
//    }

}
