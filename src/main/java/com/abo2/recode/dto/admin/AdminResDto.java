package com.abo2.recode.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public class AdminResDto {

    @Getter
    @Setter
    public static class StudyDeleteResponseDto {

        @NotEmpty
        private Long studyId;

        public StudyDeleteResponseDto(Long studyId) {
            this.studyId = studyId;
        }
    }

    @Getter
    @Setter
    public static class MemberRoleResDto {

        @NotEmpty
        private Long userId;

        @NotEmpty
        private String newRole;

        @NotEmpty
        private LocalDateTime updatedAt;

        @Builder
        public MemberRoleResDto(Long userId, String newRole, LocalDateTime updatedAt) {
            this.userId = userId;
            this.newRole = newRole;
            this.updatedAt = updatedAt;
        }
    }

}
