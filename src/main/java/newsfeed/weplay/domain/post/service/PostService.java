package newsfeed.weplay.domain.post.service;

import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.auth.dto.AuthUser;
import newsfeed.weplay.domain.post.dto.PostRequestDto;
import newsfeed.weplay.domain.post.dto.PostResponseDto;
import newsfeed.weplay.domain.post.repository.PostRepository;
import newsfeed.weplay.domain.post.entity.Post;
import newsfeed.weplay.domain.user.entity.User;
import newsfeed.weplay.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 뉴스피드 조회 (모든 게시물을 최신순으로 조회)
    public Page<PostResponseDto> getNewsFeed(AuthUser authUser, int page, int size) {
        Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
        return posts.map(this::convertToResponseDto);
    }

    public Page<PostResponseDto> getAllPosts(AuthUser authUser, int page, int size) {
        Page<Post> posts = postRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
        return posts.map(this::convertToResponseDto);
    }

    public Optional<PostResponseDto> getPostById(AuthUser authUser, Long id) {
        return postRepository.findById(id).map(this::convertToResponseDto);
    }

    public PostResponseDto createPost(AuthUser authUser, PostRequestDto postRequestDto) {
        User user = userRepository.findById(authUser.getUserId()).orElseThrow();
        Post post = convertToEntity(postRequestDto);
        post.setUser(user);
        Post createdPost = postRepository.save(post);
        return convertToResponseDto(createdPost);
    }

    public PostResponseDto updatePost(AuthUser authUser, Long id, PostRequestDto postRequestDto) {
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setTitle(postRequestDto.getTitle());
            post.setContent(postRequestDto.getContent());
            post.setCity(postRequestDto.getCity());
            post.setUpdatedAt(LocalDateTime.now());

            Post updatedPost = postRepository.save(post);
            return convertToResponseDto(updatedPost);
        } else {
            throw new RuntimeException("Post not found with id " + id);
        }
    }

    public void deletePost(AuthUser authUser, Long id) {
        postRepository.deleteById(id);
    }

    private PostResponseDto convertToResponseDto(Post post) {
        PostResponseDto dto = new PostResponseDto();
        dto.setId(post.getId());
        dto.setUserId(post.getUser() != null ? post.getUser().getId() : null);
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCity(post.getCity());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        return dto;
    }

    private Post convertToEntity(PostRequestDto dto) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setCity(dto.getCity());
        return post;
    }
}