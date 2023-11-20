package com.abo2.recode.service;

import com.abo2.recode.domain.post.Post;
import com.abo2.recode.domain.post.PostRepository;
import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.studyroom.StudyRoomRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.post.PostDetailRespDto;
import com.abo2.recode.dto.post.PostReqDto;
import com.abo2.recode.dto.post.PostRespDto;
import com.abo2.recode.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final StudyRoomRepository studyRoomRepository;


    // 게시글 목록
    public List<PostRespDto.PostListRespDto> postList(Long studyId) {

        List<Post> posts = postRepository.findPostsByStudyRoomId(studyId);

        if (posts.isEmpty()) {
            throw new CustomApiException("해당 스터디에 게시글이 존재하지 않습니다.");
        }

        return posts.stream()
                .map(PostRespDto.PostListRespDto::new)
                .collect(Collectors.toList());
    }


    // 게시글 작성
    public PostRespDto writePost(Long userId, Long studyRoomId, PostReqDto.PostWriteReqDto postWriteReqDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException("User not found with id: " + userId));

        // Find the study room by studyRoomId
        StudyRoom studyRoom = studyRoomRepository.findById(postWriteReqDto.getStudyRoomId().getId())
                .orElseThrow(() -> new CustomApiException("StudyRoom not found with id: " + studyRoomId));

        // Post 엔티티를 저장
        Post post = new Post();
        post.setTitle(postWriteReqDto.getTitle());
        post.setContent(postWriteReqDto.getContent());
        post.setCategory(postWriteReqDto.getCategory());
        post.setStudyRoom(studyRoom);
        post.setUser(user);


        Post savedPost = postRepository.save(post);

        // PostRespDto 를 생성하고 값을 채워서 반환
        return new PostRespDto(savedPost);
    }


    // 게시글 상세보기
    public PostDetailRespDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomApiException("게시글이 존재하지 않습니다. ID: " + postId));

        return new PostDetailRespDto(post);
    }


    // 게시글 수정
    public PostRespDto updatePost(Long postId, PostReqDto.PostWriteReqDto postWriteReqDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomApiException("게시글이 존재하지 않습니다. ID: " + postId));

        post.setTitle(postWriteReqDto.getTitle());
        post.setContent(postWriteReqDto.getContent());
        post.setCategory(postWriteReqDto.getCategory());

        return new PostRespDto(post);
    }


    // 게시글 삭제
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomApiException("게시글이 존재하지 않습니다. ID: " + postId));

        postRepository.delete(post);
    }
}

