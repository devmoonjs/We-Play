package newsfeed.weplay.domain.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.user.dto.request.LoginRequestDto;
import newsfeed.weplay.domain.user.dto.request.SignupRequestDto;
import newsfeed.weplay.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto, HttpServletResponse servletResponse) {
        userService.signup(requestDto, servletResponse);
        return new ResponseEntity<>("signup success", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletResponse servletResponse) {
        userService.login(requestDto, servletResponse);
        return new ResponseEntity<>("login success", HttpStatus.OK);
    }
}
