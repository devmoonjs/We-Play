package newsfeed.weplay.domain.user.controller;

import jakarta.validation.Valid;
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

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return new ResponseEntity<>("signup success", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto requestDto) {
        userService.login(requestDto);
        return new ResponseEntity<>("login success", HttpStatus.OK);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<UserResponseDto> getUserProfile(@PathVariable Long user_id) {
        return new ResponseEntity<>(userService.getUser(user_id), HttpStatus.OK);
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<String> updateUserProfile(@PathVariable Long user_id,
                                                    @Valid @RequestBody UpdateProfileRequestDto requestDto) {
        userService.updateUserProfile(user_id, requestDto);
        return new ResponseEntity<>("update success", HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long user_id, @Valid @RequestBody DeleteUserRequestDto requestDto) {
        userService.deleteUser(user_id, requestDto);
        return new ResponseEntity<>("delete success", HttpStatus.OK);
    }
}
