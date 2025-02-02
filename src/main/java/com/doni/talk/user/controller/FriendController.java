package com.doni.talk.user.controller;

import com.doni.talk.global.security.jwt.service.CustomUserDetails;
import com.doni.talk.user.dto.request.UserSearchDTO;
import com.doni.talk.user.dto.response.SimpleProfileDTO;
import com.doni.talk.user.dto.response.SearchProfileDTO;
import com.doni.talk.user.service.FriendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class FriendController {
    private final FriendService friendService;

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<SimpleProfileDTO>> getFriends(@PathVariable("id") Long id) {
        return ResponseEntity.ok(friendService.getFriendList(id));
    }

    @PostMapping("/{id}/friends")
    public ResponseEntity<SearchProfileDTO> addFriend(@PathVariable("id") Long id, @RequestParam("otherId") Long otherId) {
        return ResponseEntity.ok(friendService.addFriend(id, otherId));
    }

    @GetMapping("/search")
    public ResponseEntity<SearchProfileDTO> searchFriend(@AuthenticationPrincipal CustomUserDetails user,
                                                         @Valid @RequestPart(value = "param") UserSearchDTO param) {
        return ResponseEntity.ok(friendService.getUserBySearch(user.getUser(), param));
    }
}
