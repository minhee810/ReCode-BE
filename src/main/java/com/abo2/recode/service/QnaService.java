package com.abo2.recode.service;

import com.abo2.recode.domain.Qna.Qna;
import com.abo2.recode.domain.Qna.QnaRepository;
import com.abo2.recode.dto.qna.QnaReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;

    @Transactional
    public void postQna(QnaReqDTO qnaReqDTO) {

        Qna qna = Qna.builder()
                .user_id(qnaReqDTO.getUser_id())
                .title(qnaReqDTO.getTitle())
                .category(qnaReqDTO.getCategory())
                .content(qnaReqDTO.getContent())
                .createdAt(qnaReqDTO.getCreatedAt())
                .updatedAt(qnaReqDTO.getUpdatedAt())
                .build();

        qnaRepository.save(qna);
    }

    @Transactional
    public List<Qna> qnaList() {

        return qnaRepository.findAll();
    }

    public Qna qna(Long qnaId) {
        return qnaRepository.findById(qnaId).orElseThrow();

    }

}
