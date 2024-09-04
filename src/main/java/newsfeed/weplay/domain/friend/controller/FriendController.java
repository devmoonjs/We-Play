package newsfeed.weplay.domain.friend.controller;

import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.auth.dto.AuthUser;
import newsfeed.weplay.domain.filter.annotaion.Auth;
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
    public ResponseEntity<List<FriendSimpleResponseDto>> getFriendList(@Auth AuthUser authUser) {
        List<FriendSimpleResponseDto> friends = friendService.getFriendList(authUser);
        return ResponseEntity.ok().body(friends);
    }

    @GetMapping("/{status}/{userId}")
    public ResponseEntity<Void> acceptFriend(
            @Auth AuthUser authUser,
            @PathVariable String status,
            @PathVariable Long userId) {
        friendService.acceptFriend(status, userId, authUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteFriend(@Auth AuthUser authUser, @PathVariable Long userId) {
        friendService.deleteFriend(authUser, userId);
        return ResponseEntity.ok().build();
    }
}
