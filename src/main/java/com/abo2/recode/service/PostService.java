package com.abo2.recode.service;

import com.abo2.recode.domain.post.Post;
import com.abo2.recode.domain.post.PostRepository;
import com.abo2.recode.domain.studymember.StudyMemberRepository;
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
import org.springframework.security.access.AccessDeniedException;
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
    private final StudyMemberRepository studyMemberRepository;


    // 게시글 불러오기
    public List<PostRespDto.PostListRespDto> postList(Long studyId) {

        List<Post> posts = postRepository.findPostsByStudyId(studyId);

        if (posts.isEmpty()) {
            throw new CustomApiException("해당 스터디에 게시글이 존재하지 않습니다.");
        }

        return posts.stream()
                .map(PostRespDto.PostListRespDto::new)
                .collect(Collectors.toList());
    }


    // 게시글 작성
    public PostRespDto.PostWriteRespDto writePost(Long userId, PostReqDto.PostWriteReqDto postWriteReqDto, Long studyId) {

        User user = userRepository.findById(postWriteReqDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다" + userId));

        StudyRoom studyRoom = studyRoomRepository.findById(postWriteReqDto.getStudyId())
                .orElseThrow(() -> new EntityNotFoundException("해당 스터디 룸이 존재하지 않습니다" + studyId));

        Post post = new Post();
        post.setTitle(postWriteReqDto.getTitle());
        post.setContent(postWriteReqDto.getContent());
        post.setCategory(postWriteReqDto.getCategory());
        post.setStudyRoom(studyRoom);
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        String nickname = user.getNickname();

        return new PostRespDto.PostWriteRespDto(savedPost, nickname);
    }


    // 게시글 상세보기
    public PostRespDto.PostDetailRespDto getPostById(Long postId, Long studyId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 postId에 대한 게시글을 찾을 수 없습니다: " + postId));
        StudyRoom studyRoom = studyRoomRepository.findById(studyId)
                .orElseThrow(() -> new EntityNotFoundException("해당 studyId를 찾을 수 없습니다." + studyId));

        return new PostRespDto.PostDetailRespDto(post);
    }


    //게시글 수정
    public PostRespDto.PostUpdateRespDto updatePost(Long userId, Long postId, Long studyId, PostReqDto.PostUpdateReqDto postUpdateReqDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID에 해당하는 게시글이 없습니다: " + postId));
        StudyRoom studyRoom = studyRoomRepository.findById(studyId)
                .orElseThrow(() -> new EntityNotFoundException("해당 studyId가 존재하지 않습니다." + studyId));

        // 게시글 작성자와 현재 사용자가 동일한지 확인
        if (!post.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("게시글 수정 권한이 없습니다.");
        }

        post.setTitle(postUpdateReqDto.getTitle());
        post.setContent(postUpdateReqDto.getContent());
        post.setCategory(postUpdateReqDto.getCategory());

        Post updatedPost = postRepository.save(post);

        return new PostRespDto.PostUpdateRespDto(updatedPost);
    }


    // 게시글 삭제
    public void deletePost(Long userId, Long postId, Long studyId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID에 해당하는 게시글이 없습니다. " + postId));

        StudyRoom studyRoom = studyRoomRepository.findById(studyId)
                .orElseThrow(() -> new EntityNotFoundException("해당 studyId가 존재하지 않습니다." + studyId));

        // 게시글 작성자와 현재 사용자가 동일한지 확인
        if (!post.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("게시글 삭제 권한이 없습니다.");
        }

        // 게시글 삭제 로직
        postRepository.delete(post);
    }


    // 게시글 검색
    public List<PostRespDto.PostListRespDto> searchList(Long studyId, String keyword) {

        List<Post> posts = postRepository.findPostsByKeyword(studyId, keyword);

        if (posts.isEmpty()) {
            throw new CustomApiException("해당 게시글이 존재하지 않습니다.");
        }

        return posts.stream()
                .map((PostRespDto.PostListRespDto::new))
                .collect(Collectors.toList());
    }

    // 게시글 필터링
    public List<PostRespDto.PostListRespDto> filterList(Long studyId, Integer category) {
        List<Post> posts = postRepository.findPostsByCategory(studyId, category);

        if (posts.isEmpty()) {
            throw new CustomApiException("해당 게시글이 존재하지 않습니다.");
        }

        return posts.stream()
                .map(((PostRespDto.PostListRespDto::new)))
                .collect(Collectors.toList());
    }


}

