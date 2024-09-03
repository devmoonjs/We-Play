package newsfeed.weplay.domain.post.service;

import newsfeed.weplay.domain.post.repository.PostRepository;
import newsfeed.weplay.domain.post.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<Post> getAllPosts(int page, int size) {
        return postRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post updatedPost) {
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            post.setCity(updatedPost.getCity());
            post.setUpdatedAt(LocalDateTime.now());

            return postRepository.save(post);
        } else {
            throw new RuntimeException("Post not found with id " + id);
        }
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
