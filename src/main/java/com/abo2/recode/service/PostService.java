package com.abo2.recode.service;

import com.abo2.recode.domain.post.Post;
import com.abo2.recode.domain.post.PostRepository;
import com.abo2.recode.dto.post.PostReqDto;
import com.abo2.recode.dto.post.PostRespDto;
import com.abo2.recode.handler.ex.CustomApiException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final PostRepository postRepository;

    @Transactional
    public List<PostRespDto.PostListRespDto> postList(Long studyId){

        List<Post> posts = postRepository.findPostsByStudyRoomId(studyId);

        if (posts.isEmpty()) {
            throw new CustomApiException("해당 스터디에 게시글이 존재하지 않습니다.");
        }

        return posts.stream()
                .map(PostRespDto.PostListRespDto::new)
                .collect(Collectors.toList());
    }



    public PostRespDto writePost(PostReqDto postReqDto) {
        Post post = Post.builder()
                .title(postReqDto.getTitle())
                .content(postReqDto.getContent())
                .category(postReqDto.getCategory())
                .build();

        Post savedPost = postRepository.save(post);
        return convertToResponseDto(savedPost);
    }

    private PostRespDto convertToResponseDto(Post post) {
        return PostRespDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .studyRoomId(post.getStudyRoom().getId())
                .userId(post.getUser().getId())
                .category(post.getCategory())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
