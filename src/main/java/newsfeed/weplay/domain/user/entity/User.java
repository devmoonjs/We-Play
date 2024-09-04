package newsfeed.weplay.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import newsfeed.weplay.domain.comment.entity.Comment;
import newsfeed.weplay.domain.common.entity.BaseEntity;
import newsfeed.weplay.domain.friend.entity.Friend;
import newsfeed.weplay.domain.user.dto.request.SignupRequestDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    List<Friend> users = new ArrayList<>();

    @OneToMany(mappedBy = "friendUser")
    List<Friend> friends = new ArrayList<>();

    public User(String username, String email, String password, String imgUrl, String location, Integer age) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.imgUrl = imgUrl;
        this.location = location;
        this.age = age;
    }

    public User(SignupRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.imgUrl = requestDto.getImgUrl();
        this.location = requestDto.getLocation();
        this.age = requestDto.getAge();
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
