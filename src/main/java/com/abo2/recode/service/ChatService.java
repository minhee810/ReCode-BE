package com.abo2.recode.service;

import com.abo2.recode.domain.ChatRoom.ChatRoom;
import com.abo2.recode.domain.ChatRoom.ChatRoomRepository;
import com.abo2.recode.domain.ChatRoom.ChatRoomUserLink;
import com.abo2.recode.domain.ChatRoom.ChatRoomUserLinkRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.chat.ChatReqDto;
import com.abo2.recode.handler.ex.CustomApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final ChatRoomUserLinkRepository chatRoomUserLinkRepository;
    private final ChatRoomRepository chatRoomRepository;

    private final UserRepository userRepository;

    @Autowired
    public ChatService(ChatRoomUserLinkRepository chatRoomUserLinkRepository,
                       ChatRoomRepository chatRoomRepository,UserRepository userRepository) {
        this.chatRoomUserLinkRepository = chatRoomUserLinkRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
    }

    public List<Long> getChatRoomNameListByUser(Long userId) {
       return chatRoomUserLinkRepository.getChatRoomsByUser(userId);
    }

    public List<String> getUserNameListBychatRoomId(Long chatRoomId) {
        List<Long> userIdList = chatRoomUserLinkRepository.getUser_idBychatRoomId(chatRoomId);

        List<String> usernameList = userRepository.findAllById(userIdList)
                .stream()
                .map(User::getNickname)
                .collect(Collectors.toList());

        return usernameList;
    }

    public void createChatRoom(ChatReqDto.ChatCreateReqDto chatCreateReqDto) {
        // 1.chatRoom Entity
        ChatRoom chatRoom = new ChatRoom(chatCreateReqDto.getChatRoomTitle());
        ChatRoom result = chatRoomRepository.save(chatRoom);

        // 2. chatRoomUserLink Entity
        for(Long user_id : chatCreateReqDto.getUserIdList()){
            ChatRoomUserLink chatRoomUserLink = ChatRoomUserLink.builder()
                    .chatRoomId(result)
                    .userId(userRepository.findById(user_id).orElseThrow(
                            () -> new CustomApiException("채팅방 생성 중 오류 발생 : 참여 유저가 Null입니다.")
                    ))
                    .master(chatCreateReqDto.getMaster())
                    .build();
            chatRoomUserLinkRepository.save(chatRoomUserLink);
        }
    }//createChatRoom()

    public String getchatRoomTitleBychatRoomId(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElseThrow(
                () -> new CustomApiException("특정 유저가 참여중인 채팅방 목록 출력 중 오류 발생 : 존재하지 않는 채팅방")
        ).getTitle();
    }
}
