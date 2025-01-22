package com.doni.talk.home.controller;

import com.doni.talk.global.security.jwt.service.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class HomeController {
    @GetMapping("/home")
    public ResponseEntity<String> goHome() {
        return  ResponseEntity.ok("시작화면 - 카카오 로그인 버튼");
    }

    @GetMapping("/test")
    public ResponseEntity<String> goHome2(@AuthenticationPrincipal CustomUserDetails user) {
        log.info("로그인한 유저: {}", user.getUser());
        if(user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        else return ResponseEntity.ok("로그인되었습니다: "+user.getUser().getName());
    }
}
