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
    public PostRespDto writePost(PostReqDto postReqDto) {

        try {
            Long studyRoomId = postReqDto.getStudyRoomId();
            Long userId = postReqDto.getUserId();

            if (studyRoomId == null || userId == null) {
                // studyRoomId 또는 userId가 null 일 경우에 대한 처리
                throw new IllegalArgumentException("StudyRoomId 또는 UserId가 null 입니다.");
            }

            // 데이터베이스에서 ID에 기반하여 StudyRoom 및 User 엔터티 가져옴
            StudyRoom studyRoom = studyRoomRepository.findById(postReqDto.getStudyRoomId())
                    .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 StudyRoom 을 찾을 수 없습니다: " + postReqDto.getStudyRoomId()));

            User user = userRepository.findById(postReqDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 User 를 찾을 수 없습니다: " + postReqDto.getUserId()));

            // 새로운 Post 인스턴스를 생성하고 속성을 설정
            Post post = Post.builder()
                    .title(postReqDto.getTitle())
                    .content(postReqDto.getContent())
                    .category(postReqDto.getCategory())
                    .studyRoom(studyRoom)
                    .user(user)
                    .build();

            // Post 엔티티를 저장
            Post savedPost = postRepository.save(post);

            // PostRespDto 를 생성하고 값을 채워서 반환
            return new PostRespDto(savedPost);

        } catch (IllegalArgumentException e) {
            // 예외 처리 로직 추가 (예: 로깅)
            System.out.println("Exception thrown: StudyRoomId 또는 UserId가 null 입니다.");

            e.printStackTrace(); // 실제 상황에 맞게 로깅 또는 예외 처리를 추가
            throw e; // 예외를 다시 던져서 상위 호출자에게 전달
        }
    }


    // 게시글 상세보기
    public PostDetailRespDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomApiException("게시글이 존재하지 않습니다. ID: " + postId));

        return new PostDetailRespDto(post);
    }


    // 게시글 수정
    public PostRespDto updatePost(Long postId, PostReqDto postReqDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomApiException("게시글이 존재하지 않습니다. ID: " + postId));

        post.setTitle(postReqDto.getTitle());
        post.setContent(postReqDto.getContent());
        post.setCategory(postReqDto.getCategory());

        return new PostRespDto(post);
    }


    // 게시글 삭제
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomApiException("게시글이 존재하지 않습니다. ID: " + postId));

        postRepository.delete(post);
    }
}

