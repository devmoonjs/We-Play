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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 뉴스피드 조회 (모든 게시물을 최신순으로 조회)
    public Page<PostResponseDto> getNewsFeed(AuthUser authUser, int page, int size) {
        Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
        return posts.map(PostResponseDto::new);
    }

    public Page<PostResponseDto> getAllPosts(int page, int size) {
        Page<Post> posts = postRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
        return posts.map(PostResponseDto::new);
    }

    public PostResponseDto getPostById(Long id) {
        return new PostResponseDto(postRepository.findById(id).orElseThrow());
    }

    @Transactional
    public PostResponseDto createPost(AuthUser authUser, PostRequestDto postRequestDto) {
        User user = userRepository.findById(authUser.getUserId()).orElseThrow();
        Post post = convertToEntity(postRequestDto);
        post.setUser(user);
        Post createdPost = postRepository.save(post);
        return new PostResponseDto(createdPost);
    }

    @Transactional
    public PostResponseDto updatePost(AuthUser authUser, Long id, PostRequestDto postRequestDto) {
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setTitle(postRequestDto.getTitle());
            post.setContent(postRequestDto.getContent());
            post.setCity(postRequestDto.getCity());

            Post updatedPost = postRepository.save(post);
            return new PostResponseDto(updatedPost);
        } else {
            throw new RuntimeException("Post not found with id " + id);
        }
    }

    @Transactional
    public void deletePost(AuthUser authUser, Long id) {
        postRepository.deleteById(id);
    }

    private Post convertToEntity(PostRequestDto dto) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setCity(dto.getCity());
        return post;
    }
}