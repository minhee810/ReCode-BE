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
import com.abo2.recode.handler.ex.CustomApiException;
import com.abo2.recode.service.QnaReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QnaReplyController {

    private final QnaReplyService qnaReplyService;
    private final UserRepository userRepository;
    private final QnaRepository qnaRepository;

    //Qna 댓글 생성
    @PostMapping("/v1/qna-reply/{qna_id}")
    public ResponseEntity<?> postQnaReply(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable(value = "qna_id") Long qna_id,
            @RequestBody QnaReplyDTO qnaReplyDTO
    ) {
        qnaReplyDTO.setUser_id(loginUser.getUser().getId());

        User user = userRepository.findById(loginUser.getUser().getId()).orElseThrow(
                () -> new CustomApiException("User가 존재하지 않습니다!")
        );
        Qna qna = qnaRepository.findById(qna_id).orElseThrow(
                () -> new CustomApiException("Qna가 존재하지 않습니다!")
        );

        if (user.getId() == qna.getUser_id().getId() || user.getRole() == UserEnum.ADMIN) {

            qnaReplyDTO.setQna_id(qna_id);
            qnaReplyService.postQnaReply(qnaReplyDTO);

            return new ResponseEntity<>(new ResponseDto<>(1, "Qna 댓글 작성 성공", qnaReplyDTO), HttpStatus.OK);

        }
        return new ResponseEntity<>(new ResponseDto<>(-1, "권한 없음", null), HttpStatus.BAD_REQUEST);
    }

    //Qna 댓글 조회
    @GetMapping("/v1/qna-reply/{qna_id}")
    public ResponseEntity<?> qnaReplys(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable(value = "qna_id") Long qna_id,
            QnaReplyDTO qnaReplyDTO) {
        qnaReplyDTO.setUser_id(loginUser.getUser().getId());

        qnaReplyDTO.setQna_id(qna_id);
        List<QnaReply> qnaReplies = qnaReplyService.qnaReplies();

        return new ResponseEntity<>(new ResponseDto<>(1, "Qna 댓글 조회 성공", qnaReplies), HttpStatus.OK);
    }

    //Qna 댓글 수정
    @PutMapping("/v1/qna-reply/{qna_id}/{reply_id}")
    public ResponseEntity<?> qnaReplyModify(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable(value = "qna_id") Long qna_id,
            @PathVariable(value = "reply_id") Long reply_id,
            @RequestBody QnaReplyDTO qnaReplyDTO) {

        qnaReplyDTO.setUser_id(loginUser.getUser().getId());

        User user = userRepository.findById(loginUser.getUser().getId()).orElseThrow(
                () -> new CustomApiException("User가 존재하지 않습니다!")
        );
        Qna qna = qnaRepository.findById(qna_id).orElseThrow(
                () -> new CustomApiException("Qna가 존재하지 않습니다!")
        );

        if (qna.getUser_id().getId() == user.getId()) {

            qnaReplyDTO.setQna_id(qna_id);
            qnaReplyService.qnaReplyModify(qnaReplyDTO, reply_id);

            return new ResponseEntity<>(new ResponseDto<>(1, "Qna 댓글 수정 성공", qnaReplyDTO), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto<>(-1, "권한 없음", null), HttpStatus.BAD_REQUEST);
    }

    //Qna 댓글 삭제 (관리자 권한)
    @Secured(value = "ROLE_ADMIN")
    @DeleteMapping("/admin/v1/qna-reply/{qna_id}/{reply_id}")
    public ResponseEntity<?> qnaReplyDelete(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable(value = "qna_id") Long qna_id,
            @PathVariable Long reply_id,
            QnaReplyDTO qnaReplyDTO,
            Qna qna) {
        qnaReplyDTO.setUser_id(loginUser.getUser().getId());

        User user = userRepository.findById(loginUser.getUser().getId()).orElseThrow(
                () -> new CustomApiException("User가 존재하지 않습니다!")
        );

        if (qna.getUser_id() == user || user.getRole() == UserEnum.ADMIN) {

            qnaReplyDTO.setQna_id(qna_id);
            qnaReplyService.qnaReplyDelete(reply_id);

            return new ResponseEntity<>(new ResponseDto<>(1, "Qna 댓글 삭제 성공", qnaReplyDTO), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto<>(-1, "권한 없음", null), HttpStatus.BAD_REQUEST);
    }
}
