package com.abo2.recode.domain.chatroom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ChatRoom {

    @Id
    @Column(name = "chat_room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //ChatRoom 일련번호

    @Column(name="title")
    private String title;

    public ChatRoom(String title) {
        this.title = title;
    }
}
