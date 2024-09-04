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
        // 본인
        User user = userRepository.findById(authUser.getUserId()).orElseThrow();

        // 개인 게시글
        List<Post> posts = postRepository.findPostsByUser(user);

        // 본인 친구 목록
        List<Friend> friendList = friendRepository.findFriendsByUserAndFriendStatus(user, FriendStatusEnum.ACCEPT);

        // 뉴스피드 Posts
        List<Post> feedPosts = new ArrayList<>(posts);

        // 모든 친구에 대한 반복을 통해 게시글 가져오기
        for (Friend friend : friendList) {
            log.info("friendId = {} ", friend.getFriendUser().getId());

            // 친구 1명에 대한 게시글 리스트
            List<Post> friendPost = postRepository.findPostsByUser(friend.getFriendUser());
            // allPosts 에 담기
            feedPosts.addAll(friendPost);
        }
        List<PostResponseDto> dtos = new ArrayList<>();

        for (Post post : feedPosts) {
            dtos.add(new PostResponseDto(post));
        }

        // createdAt을 기준으로 내림차순 정렬
        dtos.sort((dto1, dto2) -> dto2.getCreatedAt().compareTo(dto1.getCreatedAt()));

        PageRequest pageRequest = PageRequest.of(page, size);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), dtos.size());
        Page<PostResponseDto> result = new PageImpl<>(dtos.subList(start, end), pageRequest, dtos.size());

        return result;
    }

    public Page<PostResponseDto> getAllPosts(int page, int size) {
        Page<Post> posts = postRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
        return posts.map(PostResponseDto::new);
    }

    @Transactional
    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        post.increaseViewCount();
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