package newsfeed.weplay.domain.friend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newsfeed.weplay.domain.auth.dto.AuthUser;
import newsfeed.weplay.domain.exception.EntityNotFoundException;
import newsfeed.weplay.domain.exception.FriendRequestStatusException;
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
@Transactional(readOnly = true)
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Transactional
    public void requestFriend(Long id, AuthUser authUser) {
        User user = findUserByEmail(authUser);
        User friendUser = findUserById(id);

        validateFriendRequest(id, user);
//        List<Friend> users = user.getUsers();
//
//        for (Friend friend : users) {
//            if (friend.getFriendUser().getId().equals(id)) {
//                if (friend.getFriendStatus() == FriendStatusEnum.ACCEPT) {
//                    throw new RuntimeException("이미 친구입니다.");
//                } else if (friend.getFriendStatus() == FriendStatusEnum.REQUESTING) {
//                    throw new RuntimeException("이미 친구 요청 보낸 유저입니다");
//                }
//            }
//        }
        Friend newFriend = new Friend(user, friendUser);
        friendRepository.save(newFriend);
    }

    private void validateFriendRequest(Long friendId, User user) {
        user.getUsers().stream()
                .filter(friend -> friend.getFriendUser().getId().equals(friendId))
                .findFirst()
                .ifPresent(friend -> {
                    if (friend.getFriendStatus() == FriendStatusEnum.ACCEPT) {
                        throw new FriendRequestStatusException("이미 친구입니다.");
                    } else if (friend.getFriendStatus() == FriendStatusEnum.REQUESTING) {
                        throw new FriendRequestStatusException("이미 요청을 보낸 유저입니다.");
                    }
                });
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 유저가 없습니다."));
    }

    private User findUserByEmail(AuthUser authUser) {
        return userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("해당 유저가 없습니다.")
        );
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
    public void acceptFriend(String status, Long userId, AuthUser authUser) {
        FriendStatusEnum statusEnum = convertToEnum(status);

        User user = findUserById(authUser.getUserId());
        List<Friend> friends = friendRepository.findFriendsByFriendUser(user);

        for (Friend friend : friends) {
            if (friend.getUser().getId().equals(userId)) {
                friend.setFriendStatus(statusEnum);
                friendRepository.save(friend);
                return;
            }
        }
    }

    @Transactional
    public void deleteFriend(AuthUser authUser, Long friendId) {
        User user = findUserById(authUser.getUserId());
        User friendUser = findUserById(friendId);

        friendRepository.deleteFriendByFriendUserAndUser(friendUser, user);
    }

    public FriendStatusEnum convertToEnum(String status) {
        try {
            return FriendStatusEnum.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("존재하지 않은 상태 요청 : " + status);
        }
    }
}
