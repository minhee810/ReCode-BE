package com.abo2.recode.service;

import com.abo2.recode.domain.post.Post;
import com.abo2.recode.domain.post.PostRepository;
import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.studyroom.StudyRoomRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.post.PostReqDto;
import com.abo2.recode.dto.post.PostRespDto;
import com.abo2.recode.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void writePost(PostReqDto.PostWriteReqDto postWriteReqDto) {

        Post savedPost = postWriteReqDto.toEntity();
        postRepository.save(savedPost);
    }


//    // 게시글 상세보기
//    public PostRespDto getPostById(Long post_id) {
//        Post post = postRepository.findById(post_id)
//                .orElseThrow(() -> new CustomApiException("해당 postId에 대한 게시글을 찾을 수 없습니다: " + post_id));
//
//        return new PostRespDto(post);
//    }


/*
    // 게시글 수정
    public PostRespDto updatePost(Long post_id, PostReqDto postReqDto) {
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new CustomApiException("해당 postId에 대한 게시글을 찾을 수 없습니다: " + post_id));

        post.setTitle(postReqDto.getTitle());
        post.setContent(postReqDto.getContent());
        post.setCategory(postReqDto.getCategory());

        Post updatedPost = postRepository.save(post);

        return new PostRespDto(updatedPost);
    }
*/


    // 게시글 삭제
    public void deletePost(Long post_id) {
        if (!postRepository.existsById(post_id)) {
            throw new CustomApiException("해당 postId에 대한 게시글을 찾을 수 없습니다: " + post_id);
        }

        postRepository.deleteById(post_id);
    }


}

