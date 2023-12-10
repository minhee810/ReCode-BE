package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.badge.BadgeReqDto;
import com.abo2.recode.dto.badge.BadgeRespDto;
import com.abo2.recode.service.BadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class BadgeController {

    private final BadgeService badgeService;

    // 스터디 멤버에게 점수 부여
    @PostMapping(value = "/v1/study/{studyId}/estimate/{userId}")
    public ResponseEntity<?> estimate(@AuthenticationPrincipal LoginUser loginUser,
                                      @PathVariable Long studyId,
                                      @PathVariable Long userId,
                                      @RequestBody BadgeReqDto.EstimateReqDto estimateReqDto){

        BadgeRespDto.EstimateRespDto estimateRespDto = badgeService.estimate(loginUser.getUser().getId(), studyId, userId, estimateReqDto);

        return new ResponseEntity<>(new ResponseDto<>(1, "평가 완료", estimateRespDto), HttpStatus.OK);
    }

//    // 사용자의 뱃지 정보 가져오기
//    @GetMapping(value = "/get-badge/{userId}/")
//    public ResponseEntity<?> getBadge(@RequestParam Long userId)
}
