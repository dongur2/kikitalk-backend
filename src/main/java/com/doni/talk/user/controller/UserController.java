package com.doni.talk.user.controller;

import com.doni.talk.global.security.jwt.service.CustomUserDetails;
import com.doni.talk.user.dto.request.UserSearchDTO;
import com.doni.talk.user.dto.request.UserUpdateDTO;
import com.doni.talk.user.dto.request.UserSignUpDTO;
import com.doni.talk.user.dto.response.SimpleProfileDTO;
import com.doni.talk.user.dto.response.UserProfileDTO;
import com.doni.talk.user.dto.response.UserSearchProfileDTO;
import com.doni.talk.user.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ResponseEntity<String> signUpForm() {
        return ResponseEntity.ok("회원가입 폼 - 닉네임, 번호, 생년월일 입력 후 요청");
    }

    @PostMapping
    public ResponseEntity<HttpStatus> signUp(@Valid @RequestPart("info") UserSignUpDTO userInfo) {
        userService.join(userInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserProfileById(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserProfileDTO> updateUserProfile(@AuthenticationPrincipal CustomUserDetails user, @PathVariable("id") Long id,
                                                            @Valid @RequestPart(value = "info") UserUpdateDTO userInfo) {
        // 요청한 id와 현재 로그인한 사용자의 id가 다른 경우
        if (!user.getUser().getId().equals(id)) throw new AccessDeniedException("권한이 없습니다.");
        return ResponseEntity.ok(userService.updateUserProfile(id, userInfo));
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<SimpleProfileDTO>> getFriends(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getFriendList(id));
    }

    @GetMapping("/search")
    public ResponseEntity<UserSearchProfileDTO> searchFriend(@AuthenticationPrincipal CustomUserDetails user,
                                                             @Valid @RequestPart(value = "param") UserSearchDTO param) {
        return ResponseEntity.ok(userService.getUserBySearch(user.getUser(), param));
    }

    @PostMapping("/{id}/friends")
    public ResponseEntity<UserSearchProfileDTO> addFriend(@PathVariable("id") Long id, @RequestParam("otherId") Long otherId) {
        return ResponseEntity.ok(userService.addFriend(id, otherId));
    }
}
