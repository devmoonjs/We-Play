package newsfeed.weplay.domain.user.controller;

import jakarta.validation.Valid;
import newsfeed.weplay.domain.auth.dto.AuthUser;
import newsfeed.weplay.domain.filter.annotaion.Auth;
import newsfeed.weplay.domain.user.dto.request.DeleteUserRequestDto;
import newsfeed.weplay.domain.user.dto.request.UpdateProfileRequestDto;
import newsfeed.weplay.domain.user.dto.request.LoginRequestDto;
import newsfeed.weplay.domain.user.dto.request.SignupRequestDto;
import newsfeed.weplay.domain.user.dto.response.UserResponseDto;
import newsfeed.weplay.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<UserResponseDto> getUserProfile(@Auth AuthUser authUser) {
        return new ResponseEntity<>(userService.getUser(authUser), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<String> updateUserProfile(@Auth AuthUser authUser,
                                                    @Valid @RequestBody UpdateProfileRequestDto requestDto) {
        userService.updateUserProfile(authUser, requestDto);
        return new ResponseEntity<>("update success", HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteUser(@Auth AuthUser authUser, @Valid @RequestBody DeleteUserRequestDto requestDto) {
        userService.deleteUser(authUser, requestDto);
        return new ResponseEntity<>("delete success", HttpStatus.OK);
    }
}
