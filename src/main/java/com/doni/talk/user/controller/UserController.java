package com.doni.talk.user.controller;

import com.doni.talk.user.domain.User;
import com.doni.talk.user.dto.request.UserSignUpDTO;
import com.doni.talk.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ResponseEntity<?> signUpForm() {
        return ResponseEntity.ok("회원가입 폼 - 닉네임, 번호, 생년월일 입력 후 요청");
    }

    @PostMapping
    public ResponseEntity<?> signUp(@RequestPart("info") UserSignUpDTO userInfo) {
        User joined = userService.join(userInfo);
        return ResponseEntity.ok(joined);
    }
}
