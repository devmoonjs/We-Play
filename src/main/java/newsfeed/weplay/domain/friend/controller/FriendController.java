package newsfeed.weplay.domain.friend.controller;

import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.auth.dto.AuthUser;
import newsfeed.weplay.domain.filter.annotaion.Auth;
import newsfeed.weplay.domain.friend.dto.response.FriendSendDto;
import newsfeed.weplay.domain.friend.dto.response.FriendSimpleResponseDto;
import newsfeed.weplay.domain.friend.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/friends")
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/request/{id}")
    public ResponseEntity<String> requestFriend(@PathVariable Long id, @Auth AuthUser authUser) {
        friendService.requestFriend(id, authUser);
        return ResponseEntity.ok().body("친구 요청이 완료되었습니다.");
    }

    @GetMapping("/requests")
    public ResponseEntity<FriendSendDto> getFriendList(@Auth AuthUser authUser) {
        return ResponseEntity.ok().body(friendService.getFriendList(authUser));
    }

    @GetMapping("/{status}/{userId}")
    public ResponseEntity<String> acceptFriend(
            @Auth AuthUser authUser,
            @PathVariable String status,
            @PathVariable Long userId) {
        friendService.acceptFriend(status, userId, authUser);
        return ResponseEntity.ok().body("친구 상태가 변경되었습니다.");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteFriend(@Auth AuthUser authUser, @PathVariable Long userId) {
        friendService.deleteFriend(authUser, userId);
        return ResponseEntity.ok().body("친구 삭제가 완료되었습니다.");
    }
}
