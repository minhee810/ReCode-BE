package com.abo2.recode.service;

import com.abo2.recode.domain.Qna.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class QnaService {

    private final QnaRepository qnaRepository;

//    @Transactional
//    public void postQna(QnaReqDTO qnaReqDTO){
//
//        Qna qna = qnaReqDTO.
//        qnaRepository.save(qna);
//    }


}
