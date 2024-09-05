package newsfeed.weplay.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newsfeed.weplay.domain.auth.dto.AuthUser;
import newsfeed.weplay.domain.friend.entity.Friend;
import newsfeed.weplay.domain.friend.entity.FriendStatusEnum;
import newsfeed.weplay.domain.friend.repository.FriendRepository;
import newsfeed.weplay.domain.post.dto.PostRequestDto;
import newsfeed.weplay.domain.post.dto.PostResponseDto;
import newsfeed.weplay.domain.post.entity.Post;
import newsfeed.weplay.domain.post.repository.PostRepository;
import newsfeed.weplay.domain.user.entity.User;
import newsfeed.weplay.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    // 뉴스피드 조회 (모든 게시물을 최신순으로 조회)
    public Page<PostResponseDto> getNewsFeed(AuthUser authUser, int page, int size) {

        User user = userRepository.findById(authUser.getUserId()).orElseThrow();
        List<Post> posts = postRepository.findPostsByUser(user);

        List<Friend> friendList = friendRepository.findFriendsByUserAndFriendStatus(user, FriendStatusEnum.ACCEPT);

        List<Post> feedPosts = new ArrayList<>(posts);

        for (Friend friend : friendList) {
            List<Post> friendPost = postRepository.findPostsByUser(friend.getFriendUser());
            feedPosts.addAll(friendPost);
        }

        List<PostResponseDto> feeds = new ArrayList<>(feedPosts.stream()
                .map(PostResponseDto::new).toList());

        feeds.sort((dto1, dto2) -> dto2.getCreatedAt().compareTo(dto1.getCreatedAt()));

        return pagingFeeds(page, size, feeds);
    }

    private static Page<PostResponseDto> pagingFeeds(int page, int size, List<PostResponseDto> feeds) {
        PageRequest pageRequest = PageRequest.of(page, size);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), feeds.size());
        return new PageImpl<>(feeds.subList(start, end), pageRequest, feeds.size());
    }

    public Page<PostResponseDto> getAllPosts(int page, int size) {
        Page<Post> posts = postRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
        return posts.map(PostResponseDto::new);
    }

    @Transactional
    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        post.increaseViewCount(); //조회수 증가를 카운트 하기 위한 해당아이디의 게시글 생성
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