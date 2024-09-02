package newsfeed.weplay.domain.friend.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newsfeed.weplay.domain.friend.dto.response.FriendSimpleResponseDto;
import newsfeed.weplay.domain.friend.entity.Friend;
import newsfeed.weplay.domain.friend.entity.FriendStatusEnum;
import newsfeed.weplay.domain.friend.repository.FriendRepository;
import newsfeed.weplay.domain.friend.repository.FriendRepository;
import newsfeed.weplay.domain.jwt.JwtUtil;
import newsfeed.weplay.domain.user.entity.User;
import newsfeed.weplay.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendService {
    private final FriendRepository friendRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    public void requestFriend(Long id, HttpServletRequest servletRequest) {

        // 이미 친구인지 확인
        Claims info = jwtUtil.getUserFromToken(servletRequest);
        User user = userRepository.findByUsername(info.getSubject()).orElseThrow();

//        List<Friend> friends = user.getFriends();
        List<Friend> users = user.getUsers();
        for (Friend friend : users) {
            log.info("User : {}" , friend.getUser().getUsername());
            log.info("friend : {}", friend.getFriendUser().getUsername());
        }

        for (Friend friend : users) {
            if (friend.getFriendUser().getId().equals(id)) {
                if (friend.getFriendStatus() == FriendStatusEnum.ACCEPT) {
                    throw new RuntimeException("이미 친구입니다.");
                } else if (friend.getFriendStatus() == FriendStatusEnum.REQUESTING) {
                    throw new RuntimeException("이미 친구 요청 보낸 유저입니다");
                }
            }
        }

        // 저장
        User friendUser = userRepository.findById(id).orElseThrow();
        Friend newFriend = new Friend(user, friendUser);
        friendRepository.save(newFriend);
    }

    public List<FriendSimpleResponseDto> getFriendList(HttpServletRequest servletRequest) {
        Claims info = jwtUtil.getUserFromToken(servletRequest);
        User user = userRepository.findByUsername(info.getSubject()).orElseThrow();

        List<Friend> users = user.getUsers();
        List<FriendSimpleResponseDto> friends = new ArrayList<>();

        for (Friend friend : users) {
            User firendUser = friend.getFriendUser();
            friends.add(new FriendSimpleResponseDto(firendUser.getId(), firendUser.getUsername(), friend.getFriendStatus()));
        }
        return friends;
    }
}
