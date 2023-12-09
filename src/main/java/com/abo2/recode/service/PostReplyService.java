package com.abo2.recode.service;


import com.abo2.recode.domain.post.Post;
import com.abo2.recode.domain.post.PostReply;
import com.abo2.recode.domain.post.PostReplyRepository;
import com.abo2.recode.domain.post.PostRepository;
import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.studyroom.StudyRoomRepository;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostReplyService {

    private final PostReplyRepository postReplyRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final StudyRoomRepository studyRoomRepository;


    // 게시글 댓글 작성
    public PostRespDto.PostReplyRespDto createPostReply(Long userId, Long postId, Long studyId, PostReqDto.PostReplyReqDto postReplyReqDto) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 postId가 없습니다." + postId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 userId가 없습니다" + userId));

        StudyRoom studyRoom = studyRoomRepository.findById(studyId)
                .orElseThrow(() -> new EntityNotFoundException("해당 studyId가 존재하지 않습니다." + studyId));


        PostReply postReply = new PostReply();
        postReply.setPost(post);
        postReply.setUser(user);
        postReply.setContent(postReplyReqDto.getContent());


        PostReply savedPostReply = postReplyRepository.save(postReply);
        String nickName = user.getNickname();

        return new PostRespDto.PostReplyRespDto(savedPostReply, nickName);
    }

    // 댓글 상세보기
    public List<PostRespDto.PostReplyRespDto> getPostReply(Long userId, Long studyId, Long postId) {
        List<PostReply> postReplyList = postReplyRepository.findByPostId(postId);

        List<PostRespDto.PostReplyRespDto> postReplyDtoList = new ArrayList<>();
        for (PostReply postReply : postReplyList) {
            // 댓글이 해당 게시글에 속해있는지 확인
            if (!postReply.getPost().getId().equals(postId)) {
                throw new IllegalArgumentException("댓글이 해당 게시글에 속하지 않습니다.");
            }

            postReplyDtoList.add(new PostRespDto.PostReplyRespDto(postReply, postReply.getUser().getNickname()));
        }

        return postReplyDtoList;
    }


    // 게시글 댓글 수정
<<<<<<< HEAD
    public PostRespDto.PostReplyRespDto updatePostReply(Long userId,Long studyId, Long postId, Long postReplyId, PostReqDto.PostReplyReqDto postReplyReqDto) {
=======
    public PostRespDto.PostReplyRespDto updatePostReply(Long userId, Long studyId, Long postId, Long postreplyId, PostReqDto.PostReplyReqDto postReplyReqDto) {
>>>>>>> ba0d9111227bca8e3d8d6488bfd74a735ce7afbc

        StudyRoom studyRoom = studyRoomRepository.findById(studyId)
                .orElseThrow(() -> new EntityNotFoundException("해당 스터디룸이 없습니다." + studyId));

<<<<<<< HEAD
        PostReply postReply = postReplyRepository.findById(postReplyId)
                .orElseThrow(() -> new EntityNotFoundException("해당 댓글이 없습니다." + postReplyId));
=======
        PostReply postReply = postReplyRepository.findById(postreplyId)
                .orElseThrow(() -> new EntityNotFoundException("해당 댓글이 없습니다." + postreplyId));
>>>>>>> ba0d9111227bca8e3d8d6488bfd74a735ce7afbc

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
<<<<<<< HEAD
=======

>>>>>>> ba0d9111227bca8e3d8d6488bfd74a735ce7afbc
    public void deletePostReply(Long userId,Long studyId, Long postId, Long postReplyId) {
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



