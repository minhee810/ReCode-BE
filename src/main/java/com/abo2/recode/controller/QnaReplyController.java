package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.domain.qna.Qna;
import com.abo2.recode.domain.qna.QnaReply;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserEnum;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.qna.QnaReplyDTO;
import com.abo2.recode.service.QnaReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class QnaReplyController {

    private final QnaReplyService qnaReplyService;
    private final UserRepository userRepository;

    @PostMapping("/qna-reply/{id}")
    public ResponseEntity<?> postQnaReply(@AuthenticationPrincipal LoginUser loginUser, @PathVariable Long id, QnaReplyDTO qnaReplyDTO, Qna qna) {
        qnaReplyDTO.setUser_id(loginUser.getUser().getId());
        User user = userRepository.findById(loginUser.getUser().getId()).orElseThrow();


        if (qna.getUser_id() == user || user.getRole() == UserEnum.ADMIN) {
            qnaReplyDTO.setQna_id(id);
            qnaReplyService.postQnaReply(qnaReplyDTO);

            return new ResponseEntity<>(new ResponseDto<>(1, "Qna 댓글 작성 성공", qnaReplyDTO), HttpStatus.OK);

        }
        return new ResponseEntity<>(new ResponseDto<>(-1, "권한 없음", null), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/qna-reply/{id}")
    public ResponseEntity<?> qnaReplys(@AuthenticationPrincipal LoginUser loginUser, @PathVariable Long id, QnaReplyDTO qnaReplyDTO) {
        qnaReplyDTO.setUser_id(loginUser.getUser().getId());
//        Qna qna = qnaRepository.findById(id).orElseThrow();
        qnaReplyDTO.setQna_id(id);
        List<QnaReply> qnaReplies = qnaReplyService.qnaReplies();

        return new ResponseEntity<>(new ResponseDto<>(1, "Qna 댓글 조회 성공", qnaReplies), HttpStatus.OK);
    }

    @PutMapping("qna-reply/{id}/{reply_id}")
    public ResponseEntity<?> qnaReplyModify(@AuthenticationPrincipal LoginUser loginUser, @PathVariable Long id, @PathVariable Long reply_id, QnaReplyDTO qnaReplyDTO, Qna qna) {
        qnaReplyDTO.setUser_id(loginUser.getUser().getId());
        User user = userRepository.findById(loginUser.getUser().getId()).orElseThrow();


        if (qna.getUser_id() == user) {

            qnaReplyDTO.setQna_id(id);
            qnaReplyService.qnaReplyModify(qnaReplyDTO, reply_id);
            return new ResponseEntity<>(new ResponseDto<>(1, "Qna 댓글 수정 성공", qnaReplyDTO), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto<>(-1, "권한 없음", null), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("qna-reply/{id}/{reply_id}")
    public ResponseEntity<?> qnaReplyDelete(@AuthenticationPrincipal LoginUser loginUser, @PathVariable Long id, @PathVariable Long reply_id, QnaReplyDTO qnaReplyDTO, Qna qna) {
        qnaReplyDTO.setUser_id(loginUser.getUser().getId());
        User user = userRepository.findById(loginUser.getUser().getId()).orElseThrow();

        if (qna.getUser_id() == user || user.getRole() == UserEnum.ADMIN) {
            qnaReplyDTO.setQna_id(id);
            qnaReplyService.qnaReplyDelete(reply_id);
            return new ResponseEntity<>(new ResponseDto<>(1, "Qna 댓글 삭제 성공", qnaReplyDTO), HttpStatus.OK);

        }
        return new ResponseEntity<>(new ResponseDto<>(-1, "권한 없음", null), HttpStatus.BAD_REQUEST);
    }
}
