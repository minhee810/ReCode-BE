package com.abo2.recode.service;


import com.abo2.recode.domain.post.Post;
import com.abo2.recode.domain.post.PostReply;
import com.abo2.recode.domain.post.PostReplyRepository;
import com.abo2.recode.domain.post.PostRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.post.PostReqDto;
import com.abo2.recode.dto.post.PostRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class PostReplyService {

    private final PostReplyRepository postReplyRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    // 게시글 댓글 작성
    @Transactional
    public PostRespDto.PostReplyRespDto createPostReply(PostReqDto.PostReplyReqDto postReplyReqDto) {
        Post post = postRepository.findById(postReplyReqDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("해당 ID에 해당하는 댓글이 없습니다: " + postReplyReqDto.getPostId()));

        User user = userRepository.findById(postReplyReqDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당 ID에 해당하는 사용자가 없습니다: " + postReplyReqDto.getUserId()));

        PostReply postReply = new PostReply();
        postReply.setPost(post);
        postReply.setUser(user);
        postReply.setContent(postReplyReqDto.getContent());


        PostReply savedPostReply = postReplyRepository.save(postReply);
        String nickName = user.getNickname();

        return new PostRespDto.PostReplyRespDto(savedPostReply, nickName);
    }
}



