package newsfeed.weplay;

import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.post.entity.Post;
import newsfeed.weplay.domain.post.repository.PostRepository;
import newsfeed.weplay.domain.user.entity.User;
import newsfeed.weplay.domain.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        User user = new User("tester", "test123@email.com", "1234", "urlTest", "city", 20);
        userRepository.save(user);

        Post post = new Post();
        post.setUser(user);
        post.setTitle("제목");
        post.setContent("contentTest");
        post.setCity("busan");

        postRepository.save(post);
    }
}
