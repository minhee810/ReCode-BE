package com.abo2.recode.dto.user;

import com.abo2.recode.domain.user.User;
import com.abo2.recode.util.CustomDateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UserRespDto {

    @Setter
    @Getter
    public static class LoginRespDto {
        private Long id;
        private String username;
        private String createdAt;

        public LoginRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.createdAt = CustomDateUtil.toStringFormat(user.getCreatedAt());
        }
    }

    @ToString
    @Setter
    @Getter
    public static class JoinRespDto {
        private Long id;
        private String username;
        private String nickname;

        public JoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.nickname = user.getNickname();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FindUsernameRespDto{
        private String username;
    }

    @Getter
    @Setter
    public static class UpdateUserRespDto{
        private Long id;
        private String email;
        private String nickname;

        public UpdateUserRespDto(User user){
            this.id = user.getId();
            this.email = user.getEmail();
            this.nickname = user.getNickname();
        }
    }

    @Getter
    @Setter
    public static class EssayRespDto{
        private String essay;

        public EssayRespDto(User user){
            this.essay = user.getEssay();
        }
    }

    @Getter
    @Setter
    public static class getUserInfoDto{
        private String username;
        private String nickname;
        private String email;

        public getUserInfoDto(User user){
            this.username = user.getUsername();
            this.nickname = user.getNickname();
            this.email = user.getEmail();
        }
    }
}
