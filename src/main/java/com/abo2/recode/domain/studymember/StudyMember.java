package com.abo2.recode.domain.studymember;

import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.dto.study.StudyResDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@NamedNativeQuery(
        name = "getApplicationEssayResDto",
        query = "SELECT u.username, u.email, u.essay " +
                "FROM Study_Member as sm " +
                "INNER JOIN Users as u " +
                "ON u.user_id = sm.user_id " +
                "WHERE sm.status = 0 AND sm.study_room_id = :study_room_id AND u.user_id = :user_id",
        resultSetMapping = "ApplicationEssayResDtoMapping"
)
@SqlResultSetMapping(
        name = "ApplicationEssayResDtoMapping",
        classes = @ConstructorResult(
                targetClass = StudyResDto.ApplicationEssayResDto.class,
                columns = {
                        @ColumnResult(name = "username", type = String.class),
                        @ColumnResult(name = "email", type = String.class),
                        @ColumnResult(name = "essay", type = String.class)
                }
        )
)

@NamedNativeQuery(
        name = "getApplicationResDto",
        query = "SELECT sm.user_id,u.username,sm.status,u.email " +
                "FROM Study_Member as sm " +
                "LEFT OUTER JOIN Users as u ON sm.user_id = u.user_id " +
                "WHERE sm.study_room_id=:study_room_id AND sm.status = 0",
        resultSetMapping = "ApplicationResDtoMapping"
)

@SqlResultSetMapping(
        name = "ApplicationResDtoMapping",
        classes = @ConstructorResult(
                targetClass = StudyResDto.ApplicationResDto.class,
                columns = {
                        @ColumnResult(name = "user_id", type = Long.class),
                        @ColumnResult(name = "username", type = String.class),
                        @ColumnResult(name = "status", type = Integer.class),
                        @ColumnResult(name = "email", type = String.class)
                }
        )
)

@NamedNativeQuery(
        name = "StudyMemberAndStatusListRespDto",
        query = "SELECT sm.study_room_id,sm.user_id,u.username,sr.created_by " +
                "FROM Study_Member as sm " +
                "INNER JOIN Users as u ON sm.user_id = u.user_id " +
                "INNER JOIN Study_Room as sr ON sm.study_room_id = sr.study_room_id "+
                "WHERE sm.study_room_id=:study_room_id AND sm.status = 1",
        resultSetMapping = "StudyMemberAndStatusListRespDtoMapping"
)

@SqlResultSetMapping(
        name = "StudyMemberAndStatusListRespDtoMapping",
        classes = @ConstructorResult(
                targetClass = StudyResDto.StudyMemberAndStatusListRespDto.class,
                columns = {
                        @ColumnResult(name = "user_id", type = Long.class),
                        @ColumnResult(name = "study_room_id", type = Long.class),
                        @ColumnResult(name = "username", type = String.class),
                        @ColumnResult(name = "created_by", type = Integer.class)
                }
        )
)

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class StudyMember {

    @Id
    @Column(name="study_member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id; //스터디 룸 member 일련번호

    @ManyToOne
    @JoinColumn(name = "study_room_id")
    private StudyRoom studyRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false,length = 50)
    private Integer status;

    @Builder
    public StudyMember(StudyRoom studyRoom, User user, Integer status) {
        this.studyRoom = studyRoom;
        this.user = user;
        this.status = status;
    }

}
