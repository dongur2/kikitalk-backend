package com.doni.talk.user.controller;

import com.doni.talk.global.security.jwt.service.CustomUserDetails;
import com.doni.talk.user.dto.request.UpdateProfileDTO;
import com.doni.talk.user.dto.response.DetailProfileDTO;
import com.doni.talk.user.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/{id}")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<DetailProfileDTO> getUserProfile(@PathVariable("id") Long id) {
        return ResponseEntity.ok(profileService.getUserProfileById(id));
    }

    @PostMapping
    public ResponseEntity<DetailProfileDTO> updateUserProfile(@AuthenticationPrincipal CustomUserDetails user, @PathVariable("id") Long id,
                                                              @Valid @RequestPart(value = "info") UpdateProfileDTO userInfo) {
        // 요청한 id와 현재 로그인한 사용자의 id가 다른 경우
        if (!user.getUser().getId().equals(id)) throw new AccessDeniedException("권한이 없습니다.");
        return ResponseEntity.ok(profileService.updateUserProfile(id, userInfo));
    }
}
