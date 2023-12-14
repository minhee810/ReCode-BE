package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.domain.qna.Qna;
import com.abo2.recode.domain.qna.QnaReply;
import com.abo2.recode.domain.qna.QnaRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserEnum;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.qna.QnaReplyDTO;
//import com.abo2.recode.dto.qna.QnaReplyResDto;
import com.abo2.recode.handler.ex.CustomApiException;
import com.abo2.recode.service.QnaReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class QnaReplyController {

    private final QnaReplyService qnaReplyService;
    private final UserRepository userRepository;
    private final QnaRepository qnaRepository;

    //Qna 댓글 생성
    @PostMapping("/qna-reply/{qnaId}")
    public ResponseEntity<?> postQnaReply(@AuthenticationPrincipal LoginUser loginUser, @PathVariable(value = "qnaId") Long qnaId, @RequestBody QnaReplyDTO qnaReplyDTO) {
        qnaReplyDTO.setUserId(loginUser.getUser().getId());

//        User user = userRepository.findById(loginUser.getUser().getId()).orElseThrow(
//                () -> new CustomApiException("User가 존재하지 않습니다!")
//        );
//        Qna qna = qnaRepository.findById(qnaId).orElseThrow(
//                () -> new CustomApiException("Qna가 존재하지 않습니다!")
//        );
//
//        if (Objects.equals(user.getId(), qna.getUserId().getId()) || user.getRole() == UserEnum.ADMIN) {
//
////            qnaReplyDTO.setQnaId(qnaId);
//            qnaReplyService.postQnaReply(qnaId, qnaReplyDTO);
//
////            qnaReplyDTO.setId(qnaReply.getId());
qnaReplyService.postQnaReply(qnaId, qnaReplyDTO);

System.out.println(qnaId);
        System.out.println(qnaReplyDTO.getComment());
//            return new ResponseEntity<>(new ResponseDto<>(1, "Qna 댓글 작성 성공", qnaReplyDTO), HttpStatus.OK);
//
//        }
        return new ResponseEntity<>(new ResponseDto<>(-1, "권한 없음", null), HttpStatus.BAD_REQUEST);
    }

    //Qna 댓글 조회
//    @GetMapping("/qna-reply/{qnaId}")
//    public ResponseEntity<?> qnaReplys(@AuthenticationPrincipal LoginUser loginUser, @PathVariable(value = "qnaId") Long qnaId, QnaReplyDTO qnaReplyDTO) {
//        qnaReplyDTO.setUserId(loginUser.getUser().getId());
//
//
////        qnaReplyDTO.setQnaId(qnaId);
//        List<QnaReply> qnaReplies = qnaReplyService.qnaReplies(qnaId);
//
////        List<QnaReplyResDto> qnaReplyResDtoList = new ArrayList<>();
//
////        for(QnaReply qnaReply : qnaReplies){
////            QnaReplyResDto qnaReplyResDto = QnaReplyResDto.builder()
////                    .id(qnaReply.getId())
////                    .comment(qnaReply.getComment())
////                    .createdAt(qnaReply.getCreatedAt())
////                    .updatedAt(qnaReply.getUpdatedAt())
////                    .build();
////
////            qnaReplyResDtoList.add(qnaReplyResDto);
////        }
//
//        return new ResponseEntity<>(new ResponseDto<>(1, "Qna 댓글 조회 성공", qnaReplies), HttpStatus.OK);
//    }

    //Qna 댓글 수정
//    @PutMapping("/qna-reply/{qnaId}/{qnaReplyId}")
//    public ResponseEntity<?> qnaReplyModify(
//            @AuthenticationPrincipal LoginUser loginUser,
//            @PathVariable(value = "qnaId") Long qnaId,
//            @PathVariable(value = "qnaReplyId") Long qnaReplyId,
//            @RequestBody QnaReplyDTO qnaReplyDTO) {
//
//        qnaReplyDTO.setUserId(loginUser.getUser().getId());
//
//        User user = userRepository.findById(loginUser.getUser().getId()).orElseThrow(
//                () -> new CustomApiException("User가 존재하지 않습니다!")
//        );
//        Qna qna = qnaRepository.findById(qnaId).orElseThrow(
//                () -> new CustomApiException("Qna가 존재하지 않습니다!")
//        );
//
//        if (qna.getUserId().getId() == user.getId()) {
//
//            qnaReplyDTO.setQnaId(qnaId);
//            QnaReply qnaReply = qnaReplyService.qnaReplyModify(qnaReplyDTO, qnaReplyId);
//            qnaReplyDTO.setId(qnaReply.getId());
//
//            return new ResponseEntity<>(new ResponseDto<>(1, "Qna 댓글 수정 성공", qnaReplyDTO), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(new ResponseDto<>(-1, "권한 없음", null), HttpStatus.BAD_REQUEST);
//    }

    //Qna 댓글 삭제 (관리자 권한)
    @Secured(value = "ROLE_ADMIN")
    @DeleteMapping("/qna-reply/{qnaId}/{qnaReplyId}")
    public ResponseEntity<?> qnaReplyDelete(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable(value = "qnaId") Long qnaId,
            @PathVariable Long qnaReplyId,
            QnaReplyDTO qnaReplyDTO,
            Qna qna) {
        qnaReplyDTO.setUserId(loginUser.getUser().getId());

        User user = userRepository.findById(loginUser.getUser().getId()).orElseThrow(
                () -> new CustomApiException("User가 존재하지 않습니다!")
        );

//        if (qna.getUserId() == user || user.getRole() == UserEnum.ADMIN) {
//
//            qnaReplyDTO.setQnaId(qnaId);
//            qnaReplyService.qnaReplyDelete(qnaReplyId);
//
//            return new ResponseEntity<>(new ResponseDto<>(1, "Qna 댓글 삭제 성공", qnaReplyDTO), HttpStatus.OK);
//        }
        return new ResponseEntity<>(new ResponseDto<>(-1, "권한 없음", null), HttpStatus.BAD_REQUEST);
    }
}