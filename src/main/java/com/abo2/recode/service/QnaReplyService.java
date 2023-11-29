package com.abo2.recode.service;


import com.abo2.recode.domain.qna.Qna;
import com.abo2.recode.domain.qna.QnaReply;
import com.abo2.recode.domain.qna.QnaReplyRepository;
import com.abo2.recode.domain.qna.QnaRepository;
import com.abo2.recode.dto.qna.QnaReplyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaReplyService {

    private final QnaReplyRepository qnaReplyRepository;
    private final QnaRepository qnaRepository;

    //Qna 댓글 생성
    @Transactional
    public void postQnaReply(QnaReplyDTO qnaReplyDTO) {
        Qna qna = qnaRepository.findById(qnaReplyDTO.getQna_id()).orElseThrow();
        QnaReply qnaReply = QnaReply.builder()
                .comment(qnaReplyDTO.getComment())
                .qna_id(qna)
                .build();

        qnaReplyRepository.save(qnaReply);
    }

    //Qna 댓글 조회 (목록)
    @Transactional
    public List<QnaReply> qnaReplies() {
        return qnaReplyRepository.findAll();
    }

    //Qna 댓글 수정
    @Transactional
    public void qnaReplyModify(QnaReplyDTO qnaReplyDTO, Long id) {
        Qna qna = qnaRepository.findById(qnaReplyDTO.getQna_id()).orElseThrow();
        QnaReply qnaReply = QnaReply.builder()
                .id(id)
                .comment(qnaReplyDTO.getComment())
                .qna_id(qna)
                .build();

        qnaReplyRepository.save(qnaReply);
    }

    @Transactional
    public void qnaReplyDelete(Long id) {
        qnaReplyRepository.deleteById(id);
    }
}
