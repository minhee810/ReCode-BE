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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PostReplyService {

    private final PostReplyRepository postReplyRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    // 게시글 댓글 작성
    @Transactional
    public PostRespDto.PostReplyRespDto createPostReply(Long userId, PostReqDto.PostReplyReqDto postReplyReqDto) {
        Post post = postRepository.findById(postReplyReqDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("해당 ID에 해당하는 댓글이 없습니다: " + postReplyReqDto.getPostId()));

        User user = userRepository.findById(postReplyReqDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당 ID에 해당하는 사용자가 없습니다: " + userId));

        PostReply postReply = new PostReply();
        postReply.setPost(post);
        postReply.setUser(user);
        postReply.setContent(postReplyReqDto.getContent());


        PostReply savedPostReply = postReplyRepository.save(postReply);
        String nickName = user.getNickname();

        return new PostRespDto.PostReplyRespDto(savedPostReply, nickName);
    }


    // 게시글 댓글 수정
    public PostRespDto.PostReplyRespDto updatePostReply(Long userId, Long postId, Long postReply_id, PostReqDto.PostReplyReqDto postReplyReqDto) {


        PostReply postReply = postReplyRepository.findById(postReply_id)
                .orElseThrow(() -> new EntityNotFoundException("해당 댓글이 없습니다." + postReply_id));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 postId가 존재하지 않습니다." + postId));

        // 댓글이 해당 게시글에 속해있는지 확인
        if (!postReply.getPost().getId().equals(postId)) {
            throw new IllegalArgumentException("댓글이 해당 게시글에 속하지 않습니다.");
        }

        // 댓글 작성자와 현재 사용자가 동일한지 확인
        if (!postReply.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("댓글 수정 권한이 없습니다.");
        }

        // 댓글 내용 수정
        postReply.setContent(postReplyReqDto.getContent());
        postReply.setUpdatedAt(LocalDateTime.now());

        return new PostRespDto.PostReplyRespDto(postReply, postReply.getUser().getNickname());
    }


    // 게시글 댓글 삭제
    public void deletePostReply(Long userId, Long postId, Long postReplyId) {
        PostReply postReply = postReplyRepository.findById(postReplyId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID에 해당하는 댓글이 없습니다." + postReplyId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 postId가 존재하지 않습니다." + postId));

        // 게시글 작성자와 현재 사용자가 동일한지 확인
        if (!postReply.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("게시글 삭제 권한이 없습니다.");
        }

        postReplyRepository.delete(postReply);
    }

}



