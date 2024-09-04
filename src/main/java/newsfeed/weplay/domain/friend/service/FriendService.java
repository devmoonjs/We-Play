package newsfeed.weplay.domain.friend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newsfeed.weplay.domain.auth.dto.AuthUser;
import newsfeed.weplay.domain.friend.dto.response.FriendSimpleResponseDto;
import newsfeed.weplay.domain.friend.entity.Friend;
import newsfeed.weplay.domain.friend.entity.FriendStatusEnum;
import newsfeed.weplay.domain.friend.repository.FriendRepository;
import newsfeed.weplay.domain.user.entity.User;
import newsfeed.weplay.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Transactional
    public void requestFriend(Long id, AuthUser authUser) {
        // 이미 친구인지 확인
        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                () -> new NullPointerException("해당 유저가 없습니다.")
        );

//        List<Friend> friends = user.getFriends();
        List<Friend> users = user.getUsers();

        for (Friend friend : users) {
            if (friend.getFriendUser().getId().equals(id)) {
                if (friend.getFriendStatus() == FriendStatusEnum.ACCEPT) {
                    throw new RuntimeException("이미 친구입니다.");
                } else if (friend.getFriendStatus() == FriendStatusEnum.REQUESTING) {
                    throw new RuntimeException("이미 친구 요청 보낸 유저입니다");
                }
            }
        }

        User friendUser = userRepository.findById(id).orElseThrow();
        Friend newFriend = new Friend(user, friendUser);
        friendRepository.save(newFriend);
    }

    @Transactional(readOnly = true)
    public List<FriendSimpleResponseDto> getFriendList(AuthUser authuser) {
        User user = userRepository.findByEmail(authuser.getEmail()).orElseThrow();

        List<Friend> users = user.getUsers();
        List<FriendSimpleResponseDto> friends = new ArrayList<>();

        for (Friend friend : users) {
            User firendUser = friend.getFriendUser();
            friends.add(new FriendSimpleResponseDto(firendUser.getId(), firendUser.getUsername(), friend.getFriendStatus()));
        }
        return friends;
    }

    @Transactional
    public void acceptFriend(Long userId, AuthUser authUser) {
        User user = userRepository.findById(authUser.getUserId()).orElseThrow();
        List<Friend> friends = friendRepository.findFriendsByFriendUser(user);

        for (Friend friend : friends) {
            if (friend.getUser().getId().equals(userId)) {
                friend.setFriendStatus(FriendStatusEnum.ACCEPT);
                friendRepository.save(friend);
                return;
            }
        }
    }

    @Transactional
    public void deleteFriend(AuthUser authUser, Long friendId) {
        User user = userRepository.findById(authUser.getUserId()).orElseThrow();
        log.info("UserId = {}", user.getId());

        User friendUser = userRepository.findById(friendId).orElseThrow();
        log.info("friendUserId = {}", friendUser.getId());

        friendRepository.deleteFriendByFriendUserAndUser(friendUser, user);
    }
}
