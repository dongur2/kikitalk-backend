package com.doni.talk.user.controller;

import com.doni.talk.user.dto.request.SignUpDTO;
import com.doni.talk.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public ResponseEntity<String> signUpForm() {
        return ResponseEntity.ok("회원가입 폼 - 닉네임, 번호, 생년월일 입력 후 요청");
    }

    @PostMapping
    public ResponseEntity<HttpStatus> signUp(@Valid @RequestPart("info") SignUpDTO userInfo) {
        userService.join(userInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
