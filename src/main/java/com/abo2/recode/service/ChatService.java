package com.abo2.recode.service;

import com.abo2.recode.domain.ChatRoom.ChatRoomRepository;
import com.abo2.recode.domain.ChatRoom.ChatRoomUserLinkRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
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

    public List<Long> getChatRoomsByUser(Long userId) {
       return chatRoomUserLinkRepository.getChatRoomsByUser(userId);
    }

    public List<String> getUser_nameBychatRoomId(Long chatRoomId) {
        List<Long> userIdList = chatRoomUserLinkRepository.getUser_idBychatRoomId(chatRoomId);

     /*   List<Long> chatRoomIds = links.stream()
                .map(ChatRoomUserLink::getChatRoomId)
                .collect(Collectors.toList());*/

        List<String> usernameList = userRepository.findAllById(userIdList)
                .stream()
                .map(User::getNickname)
                .collect(Collectors.toList());

        return usernameList;
    }
}
