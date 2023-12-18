package com.abo2.recode.service;


import com.abo2.recode.domain.qna.Qna;
import com.abo2.recode.domain.qna.QnaReply;
import com.abo2.recode.domain.qna.QnaReplyRepository;
import com.abo2.recode.domain.qna.QnaRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.qna.QnaReplyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class QnaReplyService {

    private final QnaReplyRepository qnaReplyRepository;
    private final QnaRepository qnaRepository;
    private final UserRepository userRepository;

    //Qna 댓글 생성
    @Transactional
    public void postQnaReply(Long qnaId, QnaReplyDTO qnaReplyDTO) {

        Qna qna = qnaRepository.findById(qnaId).orElseThrow();
        User user = userRepository.findById(qnaReplyDTO.getUserId()).orElseThrow();

        QnaReply qnaReply = QnaReply.builder()
                .comment(qnaReplyDTO.getComment())
                .createdAt(qnaReplyDTO.getCreatedAt())
                .updatedAt(qnaReplyDTO.getUpdatedAt())
                .user(user)
                .qna(qna)
                .build();

        qnaReplyRepository.save(qnaReply);
    }


    //Qna 댓글 삭제
    @Transactional
    public void qnaReplyDelete(Long id) {

        qnaReplyRepository.deleteById(id);
    }
}
