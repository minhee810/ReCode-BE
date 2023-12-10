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
    public QnaReply postQnaReply(Long qnaId, QnaReplyDTO qnaReplyDTO) {

        Qna qna = qnaRepository.findById(qnaId).orElseThrow();

        QnaReply qnaReply = QnaReply.builder()
                .comment(qnaReplyDTO.getComment())
                .qnaId(qna)
                .build();

        qnaReplyRepository.save(qnaReply);

        return qnaReply;
    }

    //Qna 댓글 목록 조회
//    @Transactional
//    public List<QnaReply> qnaReplies(Long qnaId) {
//
//        //
////        Qna qna = qnaRepository.findById(qnaId).orElseThrow();
//
//
//       return qnaReplyRepository.findAllByQnaId(qnaId);
//    }

    //Qna 댓글 수정
    @Transactional
    public QnaReply qnaReplyModify(QnaReplyDTO qnaReplyDTO, Long id, Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow();
        QnaReply qnaReply = QnaReply.builder()
                .id(id)
                .comment(qnaReplyDTO.getComment())
                .qnaId(qna)
                .build();

        qnaReplyRepository.save(qnaReply);

        return qnaReply;
    }

    //Qna 댓글 삭제
    @Transactional
    public void qnaReplyDelete(Long id) {
        qnaReplyRepository.deleteById(id);
    }
}
