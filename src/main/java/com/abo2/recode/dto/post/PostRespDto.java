package com.abo2.recode.dto.post;

import com.abo2.recode.domain.post.Post;
import com.abo2.recode.domain.studymember.StudyMember;
import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

public class PostRespDto {

    @Getter
    @Setter
    public static class PostListRespDto{
        private Long id;
        private String title;
        private String content;
        private String category;
        private LocalDateTime createdAt;
        private String nickname;

        public PostListRespDto(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.category = getCategory(post.getCategory());
            this.createdAt = post.getCreatedAt();
            this.nickname = post.getUser().getNickname();
        }

        private String getCategory(Integer category) {
            switch (category) {
                case 0:
                    return "공지사항";
                case 1:
                    return "회고록";
                case 2:
                    return "자료 공유";
                default:
                    return "전체보기";
            }
        }
    } //PostListRespDto


    @Getter
    @Setter
    public static class StudyMemberListDto{

//
//        private Long Id; //스터디 룸 member 일련번호
//
//        @ManyToOne
//        @JoinColumn(name = "study_room_id")
//        private StudyRoom studyRoom;
//
//        @ManyToOne
//        @JoinColumn(name = "user_id")
//        private User user;
//
//        @Column(nullable = false,length = 50)
//        private Integer status;

        private Long Id;
        private Long studyRoomId;
        private Long userId;
        private Integer status;

        public StudyMemberListDto(StudyMember studyMember) {
            Id = studyMember.getId();
            this.studyRoomId = studyMember.getStudyRoom().getId();
            this.userId = studyMember.getUser().getId();
            this.status = studyMember.getStatus();
        }
    }//StudyMemberListDto

}
