package newsfeed.weplay.domain.post.controller;

import newsfeed.weplay.domain.auth.dto.AuthUser;
import newsfeed.weplay.domain.filter.annotaion.Auth;
import newsfeed.weplay.domain.post.dto.PostRequestDto;
import newsfeed.weplay.domain.post.dto.PostResponseDto;
import newsfeed.weplay.domain.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 뉴스피드 조회 (현재 사용자만의 게시물을 최신순으로 조회)
    @GetMapping("/newsfeed")
    public ResponseEntity<Page<PostResponseDto>> getNewsFeed(@Auth AuthUser authUser,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        Page<PostResponseDto> posts = postService.getNewsFeed(authUser, page, size);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // 모든 게시물 조회 (페이징)
    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(@Auth AuthUser authUser,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        Page<PostResponseDto> posts = postService.getAllPosts(authUser, page, size);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // 특정 게시물 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@Auth AuthUser authUser, @PathVariable Long id) {
        return postService.getPostById(authUser, id)
                .map(post -> new ResponseEntity<>(post, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 게시물 생성
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Auth AuthUser authUser, @RequestBody PostRequestDto postRequestDto) {
        PostResponseDto createdPost = postService.createPost(authUser, postRequestDto);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // 게시물 수정
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@Auth AuthUser authUser, @PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        try {
            PostResponseDto updatedPost = postService.updatePost(authUser, id, postRequestDto);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@Auth AuthUser authUser, @PathVariable Long id) {
        try {
            postService.deletePost(authUser, id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}