package com.abo2.recode.dto.post;


import com.abo2.recode.domain.post.Post;
import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


public class PostReqDto {


    @Getter
    @Setter
    public static class PostWriteReqDto {

        private String title;
        private String content;
        private String category;
        private Long studyRoomId;  // StudyRoom 대신 실제 외래 키 값인 Long 으로 변경
        private Long userId;


    }
}