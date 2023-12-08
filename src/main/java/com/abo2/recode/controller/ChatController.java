package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.domain.ChatRoom.Chat;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.chat.ChatReqDto;
import com.abo2.recode.dto.chat.ChatResDto;
import com.abo2.recode.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ChatController {

    private ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // 특정 유저가 참여중인 채팅방 목록 출력 -> 채팅 목록
    @GetMapping(value = "/v1/chat/chat-list")
    public ResponseEntity<?> getChatRoomsByUser(
            @AuthenticationPrincipal LoginUser loginUser
            ){

        List<ChatResDto.ChatListDto> chatListDtoList = new ArrayList<>();

        // 1. 유저가 참여 중인 채팅방 목록 가져 오기
        // SELECT chatroomId FROM ChatRoomUserLink WHERE userId = ?;
        List<Long> chatRoomIdList = chatService.getChatRoomNameListByUser(loginUser.getUser().getId());

        //2. 모든 chatRoomId에 대해 각 채팅방에 참여 중인 유저 목록 출력
        // SELECT user_id as userId FROM ChatRoomUserLink WHERE chat_room_id = ?
        // 3. 각 chatRoomId에 대해 마지막 메세지 가져오기
        for(Long chatRoomId : chatRoomIdList){
            List<String> usernameList = chatService.getUserNameListBychatRoomId(chatRoomId);

            URI uri = UriComponentsBuilder
                    .fromUriString("http://localhost:8080")
                    .path("/chat/roomNum/{chatRoomId}/last-message")
                    .buildAndExpand(chatRoomId)
                    .encode()
                    .toUri();

            //getForObject
            RestTemplate restTemplate = new RestTemplate();
            String lastmessage = restTemplate.getForObject(uri,Chat.class).getMsg();

            //4. ChatListDto , chatListDtoList에 담기
            /*    ChatResDto.ChatListDto = {Long chatRoomId; List<String> usernameList; String lastMessage; }*/
            ChatResDto.ChatListDto chatListDto = ChatResDto.ChatListDto.builder()
                    .chatRoomId(chatRoomId)
                    .usernameList(usernameList)
                    .lastMessage(lastmessage)
                    .title(chatService.getchatRoomTitleBychatRoomId(chatRoomId))
                    .build();

            chatListDtoList.add(chatListDto);
        }
        return new ResponseEntity<>( new ResponseDto<>(1,"현재 참여 중인 채팅방 목록입니다.",chatListDtoList), HttpStatus.OK);
    }//getChatRoomsByUser()

    // 참여 중인 채팅방 탈퇴

    // 채팅방에서 다시 목록으로 돌아가기

    // 채팅방 생성
    @PostMapping(value = "/v1/chat/chatRoom")
    public ResponseEntity<?> createChatRoom(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody ChatReqDto.ChatCreateReqDto chatCreateReqDto
    ){
        chatCreateReqDto.setMaster(loginUser.getUser());
        // 1.chatRoom Entity,chatRoomUserLink Entity
        ChatReqDto.ChatCreateReqDto chatcreatedtoresult = chatService.createChatRoom(chatCreateReqDto);
        return new ResponseEntity<>( new ResponseDto<>(1,"채팅방을 생성했습니다.",chatcreatedtoresult), HttpStatus.OK);
    }//createChatRoom()

    // 채팅방 삭제

    // 특정 채팅방에 참여 중인 유저 목록 출력 -> 채팅 창


}//ChatController
