package newsfeed.weplay.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.auth.dto.AuthUser;
import newsfeed.weplay.domain.comment.dto.response.CommentResponseDto;
import newsfeed.weplay.domain.filter.annotaion.Auth;
import newsfeed.weplay.domain.user.dto.request.DeleteUserRequestDto;
import newsfeed.weplay.domain.user.dto.request.UpdateProfileRequestDto;
import newsfeed.weplay.domain.user.dto.response.UserResponseDto;
import newsfeed.weplay.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    // 유저 프로필 조회
    @GetMapping("")
    public ResponseEntity<UserResponseDto> getUserProfile(@Auth AuthUser authUser) {
        return new ResponseEntity<>(userService.getUser(authUser), HttpStatus.OK);
    }

    // 유저 프로필 업데이트
    @PutMapping("")
    public ResponseEntity<String> updateUserProfile(@Auth AuthUser authUser,
                                                    @Valid @RequestBody UpdateProfileRequestDto requestDto) {
        userService.updateUserProfile(authUser, requestDto);
        return new ResponseEntity<>("업데이트가 완료되었습니다.", HttpStatus.OK);
    }

    // 유저 탈퇴
    @PostMapping("")
    public ResponseEntity<String> deleteUser(@Auth AuthUser authUser, @Valid @RequestBody DeleteUserRequestDto requestDto) {
        userService.deleteUser(authUser, requestDto);
        return new ResponseEntity<>("유저가 탈퇴 되었습니다.", HttpStatus.OK);
    }

    // 유저 알림 조회
    @GetMapping("/notification")
    public ResponseEntity<List<CommentResponseDto>> getUserNotifications(@Auth AuthUser authUser) {
        return new ResponseEntity<>(userService.getUserNotifications(authUser), HttpStatus.OK);
    }
}
