package newsfeed.weplay.domain.friend.controller;

import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/accept/{id}")
    public ResponseEntity<String> requestFriend(@PathVariable Long id, @Auth AuthUser authUser) {
        friendService.requestFriend(id, authUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/accepts")
    public ResponseEntity<List<FriendSimpleResponseDto>> getFriendList(@Auth AuthUser authUser) {
        List<FriendSimpleResponseDto> friends = friendService.getFriendList(authUser);
        return ResponseEntity.ok().body(friends);
    }

    /*
        승인 ->
        1. 친구신청 받은 신청 목록을 리스트로 가져와서
        2.
     */

    @GetMapping("/accepts/{userId}")
    public ResponseEntity<Void> getFriendList(@PathVariable Long userId, @Auth AuthUser authUser) {
        friendService.acceptFriend(userId, authUser);
        return ResponseEntity.ok().build();
    }
}
