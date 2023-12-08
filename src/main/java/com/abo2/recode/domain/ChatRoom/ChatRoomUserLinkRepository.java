package com.abo2.recode.domain.ChatRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomUserLinkRepository extends JpaRepository<ChatRoomUserLink,Long> {
    @Query(value = "SELECT chat_room_id as chatRoomId FROM chat_room_user_link WHERE user_id =:userId",nativeQuery = true)
    List<Long> getChatRoomsByUser(@Param(value = "userId") Long userId);

    @Query(value = "SELECT user_id as userId FROM chat_room_user_link WHERE chat_room_id =:chatRoomId",nativeQuery = true)
    List<Long> getUser_idBychatRoomId(@Param(value = "chatRoomId")Long chatRoomId);
}
