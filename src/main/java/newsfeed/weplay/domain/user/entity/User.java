package newsfeed.weplay.domain.user.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "age")
    private Integer age;

    @Column(name = "location")
    private String location;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public User(String username, String email, String password, String imgUrl, String location, Integer age) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.imgUrl = imgUrl;
        this.location = location;
        this.age = age;
    }

    public void update(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.imgUrl = user.getImgUrl();
        this.location = user.getLocation();
        this.age = user.getAge();
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
