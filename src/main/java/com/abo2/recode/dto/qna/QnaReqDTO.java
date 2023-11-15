package com.abo2.recode.dto.qna;

import com.abo2.recode.domain.qna.Qna;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QnaReqDTO {

    private Long qna_id;
    private Long user_id;
    private String title;
    private String category;
    private String content;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;


    public static QnaReqDTO postQna(Qna entity){
return QnaReqDTO.builder()
        .title(entity.getTitle())
        .content(entity.getContent())
        .category(entity.getCategory())
        .build();
    }
}
