package com.abo2.recode.dto.post;


import com.abo2.recode.domain.post.Post;
import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
public class PostReqDto {

    public static class PostWriteReqDto {

        @NotEmpty
        private String title;

        @NotEmpty
        private String content;

        @NotEmpty
        private StudyRoom studyRoomId;

        @NotEmpty
        private User userId;

        @NotEmpty
        private Integer category;


        public Post toEntity() {
            return Post.builder()
                    .title(title)
                    .content(content)
                    .category(category)
                    .studyRoom(studyRoomId)
                    .user(userId)
                    .build();
        }


    }





}

