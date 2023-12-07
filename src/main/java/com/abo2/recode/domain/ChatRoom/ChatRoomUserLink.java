package com.abo2.recode.domain.ChatRoom;

import com.abo2.recode.domain.skill.Skill;
import com.abo2.recode.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ChatRoomUserLink {

    @Id
    @Column(name = "chat_user_link_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //ChatRoomUserLink 일련번호

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @OneToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoomId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by")
    private User master;



}
