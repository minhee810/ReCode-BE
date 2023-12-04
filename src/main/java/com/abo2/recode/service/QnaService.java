package com.abo2.recode.service;

import com.abo2.recode.domain.qna.Qna;
import com.abo2.recode.domain.qna.QnaRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.qna.QnaReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;
    private final UserRepository userRepository;

    //Qna 생성
    @Transactional
    public void postQna(QnaReqDTO qnaReqDTO) {

        User user = userRepository.findById(qnaReqDTO.getUserId()).orElseThrow();

        Qna qna = Qna.builder()
                .userId(user)
                .title(qnaReqDTO.getTitle())
                .category(qnaReqDTO.getCategory())
                .content(qnaReqDTO.getContent())
                .build();

        qnaRepository.save(qna);
    }

    //Qna 목록 조회
    @Transactional
    public List<Qna> qnaList() {
        return qnaRepository.findAll();
    }

    //Qna 단일 조회
    @Transactional
    public Qna qna(Long qnaId) {

        return qnaRepository.findById(qnaId).orElseThrow();
    }

    //Qna 수정
    @Transactional
    public void qnaModify(Long id, QnaReqDTO qnaReqDTO) {

        Qna qna = Qna.builder()
                .id(id)
                .title(qnaReqDTO.getTitle())
                .category(qnaReqDTO.getCategory())
                .content(qnaReqDTO.getContent())
                .build();

        qnaRepository.save(qna);
    }

    //Qna 삭제
    @Transactional
    public void qnaDelete(Long id) {
        qnaRepository.deleteById(id);
    }
}
