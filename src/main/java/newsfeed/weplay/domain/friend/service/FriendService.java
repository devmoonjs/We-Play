package newsfeed.weplay.domain.friend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newsfeed.weplay.domain.auth.dto.AuthUser;
import newsfeed.weplay.domain.exception.EntityNotFoundException;
import newsfeed.weplay.domain.exception.FriendRequestStatusException;
import newsfeed.weplay.domain.friend.dto.response.FriendSendDto;
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

    @Transactional(readOnly = true)
    public FriendSendDto getFriendList(AuthUser authuser) {
        User user = userRepository.findByEmail(authuser.getEmail()).orElseThrow();

        List<Friend> requestUsers = user.getUsers();
        List<Friend> responseUsers = user.getFriends();

        List<FriendSimpleResponseDto> requestUserDtos = new ArrayList<>();
        List<FriendSimpleResponseDto> responseUserDtos = new ArrayList<>();

        for (Friend friend : requestUsers) {
            User friendUser = friend.getFriendUser();
            requestUserDtos.add(new FriendSimpleResponseDto(friendUser.getId(), friendUser.getUsername(), friend.getFriendStatus()));
        }

        for (Friend friend : responseUsers) {
            User responseUser = friend.getUser();
            responseUserDtos.add(new FriendSimpleResponseDto(responseUser.getId(), responseUser.getUsername(), friend.getFriendStatus()));
        }

        return new FriendSendDto(requestUserDtos, responseUserDtos);
    }

    @Transactional
    public void acceptFriend(String status, Long userId, AuthUser authUser) {
        FriendStatusEnum statusEnum = convertToEnum(status);

        User user = findUserById(authUser.getUserId());
        List<Friend> friends = friendRepository.findFriendsByFriendUser(user);

        for (Friend friend : friends) {
            if (friend.getUser().getId().equals(userId)) {
                friend.changeFriendStatus(statusEnum);
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

    private FriendStatusEnum convertToEnum(String status) {
        try {
            return FriendStatusEnum.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("존재하지 않은 상태 요청 : " + status);
        }
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 유저가 없습니다."));
    }

    private User findUserByEmail(AuthUser authUser) {
        return userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("해당 유저가 없습니다."));
    }
}
