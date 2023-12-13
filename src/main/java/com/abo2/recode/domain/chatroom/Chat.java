package com.abo2.recode.domain.chatroom;

import lombok.Data;

import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

//MongoDB document
@Data
public class Chat {

    @Id
    private String id;
    private String msg;
    private String sender; //보내는 사람
    private Integer roomNum; //채팅방 번호

    private LocalDateTime createdAt;
}