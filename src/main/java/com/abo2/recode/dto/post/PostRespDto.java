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
        public String fileName;

        public PostWriteRespDto(Post post, String nickname) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.category = post.getCategory();
            this.createdAt = post.getCreatedAt();
            this.nickname = nickname;
            this.fileName = post.getFileName();
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

            if (post.getUser() != null) {
                this.nickname = post.getUser().getNickname();
            } else {
                this.nickname = "탈퇴한 회원입니다.";
            }
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
        private String nickName;
        private String fileName;

        @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
        private LocalDateTime createdAt;

        public PostDetailRespDto(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.category = post.getCategory();
            this.createdAt = post.getCreatedAt();
            this.fileName = post.getFileName();
            if (post.getUser() != null) {
                this.nickName = post.getUser().getNickname();
            } else {
                this.nickName = "탈퇴한 회원입니다.";
            }
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
                    return "선택하세요";
            }
        }
    }


    @Getter
    @Setter
    public static class PostUpdateRespDto {
        private Long id;
        private String title;
        private String content;
        private Integer category;

        @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
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

        @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
        private LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
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

        private Long Id;
        private Long studyId;
        private Long userId;
        private Integer status;

        public StudyMemberListDto(StudyMember studyMember) {
            Id = studyMember.getId();
            this.studyId = studyMember.getStudyRoom().getId();
            this.userId = studyMember.getUser().getId();
            this.status = studyMember.getStatus();
        }
    }
}
