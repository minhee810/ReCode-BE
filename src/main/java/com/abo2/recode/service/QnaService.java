package com.abo2.recode.service;

import com.abo2.recode.domain.qna.Qna;
import com.abo2.recode.domain.qna.QnaReply;
import com.abo2.recode.domain.qna.QnaReplyRepository;
import com.abo2.recode.domain.qna.QnaRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.qna.QnaReplyDTO;
import com.abo2.recode.dto.qna.QnaReqDTO;
import com.abo2.recode.dto.qna.QnaResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;
    private final QnaReplyRepository qnaReplyRepository;
    private final UserRepository userRepository;


    //Qna 목록 조회
    @Transactional
    public List<QnaResDTO> qnaList() {
        List<QnaResDTO> result = new ArrayList<>();

        List<Qna> qnaList = qnaRepository.findAll();
        qnaList.forEach(q -> {
            User user = q.getUser();
            QnaResDTO dto = QnaResDTO.builder()
                    .id(q.getId())
                    .content(q.getContent())
                    .title(q.getTitle())
                    .createdAt(q.getCreatedAt())
                    .updatedAt(q.getUpdatedAt())
                    .nickname(user.getNickname())
                    .role(user.getRole())
                    .userId(user.getId())
                    .build();

            result.add(dto);
        });

        return result;
    }

    //Qna 단일 조회
    @Transactional
    public QnaResDTO qna(Long qnaId) {

        Qna qna = qnaRepository.findById(qnaId).orElseThrow();
        List<QnaReply> qnaReplies = qnaReplyRepository.findByQnaId(qnaId);


        List<QnaReplyDTO> qnaReplyDTOList = new ArrayList<>();
        qnaReplies.forEach(q -> {
            QnaReplyDTO dto = QnaReplyDTO.builder()
                    .id(q.getId())
                    .qnaId(q.getId())
                    .comment(q.getComment())
                    .createdAt(q.getCreatedAt())
                    .updatedAt(q.getUpdatedAt())
                    .build();

            qnaReplyDTOList.add(dto);

        });

        return QnaResDTO.builder()
                .id(qna.getId())
                .title(qna.getTitle())
                .content(qna.getContent())
                .createdAt(qna.getCreatedAt())
                .updatedAt(qna.getUpdatedAt())
                .userId(qna.getUser().getId())
                .role(qna.getUser().getRole())
                .nickname(qna.getUser().getNickname())
                .qnaReplyList(qnaReplyDTOList)
                .build();
    }

    //Qna 생성
    @Transactional
    public void postQna(QnaReqDTO qnaReqDTO) {

        User user = userRepository.findById(qnaReqDTO.getUserId()).orElseThrow();

        Qna qna = Qna.builder()
                .user(user)
                .title(qnaReqDTO.getTitle())
                .content(qnaReqDTO.getContent())
                .createdAt(qnaReqDTO.getCreatedAt())
                .build();

        qnaRepository.save(qna);
    }

    //Qna 수정
    @Transactional
    public void qnaModify(Long qnaId, QnaReqDTO qnaReqDTO) {
Qna qna = qnaRepository.findById(qnaId).orElseThrow();
        Qna data = Qna.builder()
                .id(qna.getId())
                .title(qnaReqDTO.getTitle())
                .content(qnaReqDTO.getContent())
                .updatedAt(qnaReqDTO.getUpdatedAt())
                .build();

        qnaRepository.save(data);
    }

    //Qna 삭제
    @Transactional
    public void qnaDelete(Long id) {
        qnaRepository.deleteById(id);
    }
}