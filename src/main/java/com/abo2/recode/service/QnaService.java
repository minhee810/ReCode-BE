package com.abo2.recode.service;

import com.abo2.recode.dto.qna.QnaReqDTO;
import com.abo2.recode.entity.Qna;
import com.abo2.recode.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class QnaService {

    private final QnaRepository qnaRepository;

    @Transactional
    public void postQna(QnaReqDTO qnaReqDTO){

        Qna qna = qnaReqDTO.
        qnaRepository.save(qna);
    }


}
