package com.abo2.recode.dto.post;

import com.abo2.recode.domain.post.Post;
import com.abo2.recode.domain.post.PostReply;
import com.abo2.recode.domain.studymember.StudyMember;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


public class PostRespDto {

    private Long id;
    private String title;
    private String content;
    private Integer category;
    private LocalDateTime createdAt;
    private String nickname;


    @Getter
    @Setter
    public static class PostWriteRespDto {
        private Long id;
        private String title;
        private String content;
        private Integer category;
        private LocalDateTime createdAt;
        private String nickname;

        public PostWriteRespDto(Post post, String nickname) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.category = post.getCategory();
            this.createdAt = post.getCreatedAt();
            this.nickname = nickname;
        }

    }

    @Getter
    @Setter
    public static class PostListRespDto {
        private Long id;
        private String title;
        private String content;
        private String category;

        @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
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

    }

    @Getter
    @Setter
    public static class PostDetailRespDto {
        private Long id;
        private String title;
        private String content;
        private Integer category;
        private LocalDateTime createdAt;
        private String nickname;

        public PostDetailRespDto(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.category = post.getCategory();
            this.createdAt = post.getCreatedAt();
            this.nickname = post.getUser().getNickname();
        }
    }


    @Getter
    @Setter
    public static class PostUpdateRespDto {
        private Long id;
        private String title;
        private String content;
        private Integer category;
        private LocalDateTime updatedAt;

        public PostUpdateRespDto(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.category = post.getCategory();
            this.updatedAt = post.getUpdatedAt();
        }

    }

    @Getter
    @Setter
    public static class PostReplyRespDto {
        private Long id;
        private String content;
        private Long postId;
        private Long userId;
        private String nickName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public PostReplyRespDto(PostReply postReply, String nickName) {
            this.id = postReply.getId();
            this.content = postReply.getContent();
            this.postId = postReply.getPost().getId();
            this.userId = postReply.getUser().getId();
            this.createdAt = postReply.getCreatedAt();
            this.updatedAt = postReply.getUpdatedAt();
            this.nickName = nickName;
        }
    }

    public static class StudyMemberListDto {

//
//        private Long Id; //스터디 룸 member 일련번호
//
//        @ManyToOne
//        @JoinColumn(name = "studyId")
//        private StudyRoom studyRoom;
//
//        @ManyToOne
//        @JoinColumn(name = "userId")
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
