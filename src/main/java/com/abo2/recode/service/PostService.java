package com.abo2.recode.service;

import com.abo2.recode.domain.post.Post;
import com.abo2.recode.domain.post.PostRepository;
import com.abo2.recode.dto.post.PostDetailRespDto;
import com.abo2.recode.dto.post.PostReplyRespDto;
import com.abo2.recode.dto.post.PostReqDto;
import com.abo2.recode.dto.post.PostRespDto;
import com.abo2.recode.handler.ex.CustomApiException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final PostRepository postRepository;

    @Transactional
    public List<PostRespDto.PostListRespDto> postList(Long studyId) {

        List<Post> posts = postRepository.findPostsByStudyRoomId(studyId);

        if (posts.isEmpty()) {
            throw new CustomApiException("해당 스터디에 게시글이 존재하지 않습니다.");
        }

        return posts.stream()
                .map(PostRespDto.PostListRespDto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public PostRespDto writePost(PostReqDto postReqDto) {
        Post post = Post.builder()
                .title(postReqDto.getTitle())
                .content(postReqDto.getContent())
                .category(postReqDto.getCategory())
                .build();

        Post savedPost = postRepository.save(post);

        // PostRespDto를 생성하고 값을 채워서 반환
        return PostRespDto.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .category(savedPost.getCategory())
                .studyRoomId(savedPost.getStudyRoom().getId())
                .userId(savedPost.getUser().getId())
                .createdAt(savedPost.getCreatedAt())
                .updatedAt(savedPost.getUpdatedAt())
                .build();
    }

    @Transactional
    public PostDetailRespDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomApiException("게시글이 존재하지 않습니다. ID: " + postId));

        return new PostDetailRespDto(post);
    }
}
