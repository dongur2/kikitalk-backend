package com.doni.talk.home.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HomeController {
    @GetMapping
    public ResponseEntity<String> goHome() {
        return  ResponseEntity.ok("시작화면 - 카카오 로그인 버튼");
    }
}
