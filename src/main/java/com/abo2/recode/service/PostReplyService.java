package com.abo2.recode.service;


import com.abo2.recode.domain.post.PostReply;
import com.abo2.recode.domain.post.PostReplyRepository;
import com.abo2.recode.dto.post.PostReplyReqDto;
import com.abo2.recode.dto.post.PostReplyRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReplyService {

    private final PostReplyRepository postReplyRepository;

    public PostReplyRespDto postReply(PostReplyReqDto postReplyReqDto) {
        PostReply postReply = PostReply.builder()
                .content(postReplyReqDto.getContent())
                .build();

        // 생성된 PostReply 엔터티 저장
        PostReply savedPostReply = postReplyRepository.save(postReply);

        // 저장된 PostReply 엔터티를 PostReplyRespDto로 변환
        PostReplyRespDto postReplyRespDto = new PostReplyRespDto();
        postReplyRespDto.setId(savedPostReply.getId());
        postReplyRespDto.setContent(savedPostReply.getContent());

        return postReplyRespDto;

    }
}
