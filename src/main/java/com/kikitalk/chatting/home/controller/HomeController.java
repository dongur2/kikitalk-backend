package com.kikitalk.chatting.home.controller;

import com.kikitalk.chatting.global.security.jwt.service.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 프론트엔드 구현 전까지 사용하는 서비스 첫 화면 컨트롤러입니다.
 * 로그인 및 인증과 관련된 API를 확인하는 용도로 사용됩니다.
 *
 * @deprecated 프론트엔드 구현 이후 더 이상 사용되지 않습니다.
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class HomeController {

    /**
     * 서비스 첫 화면을 조회합니다.
     * 인증되지 않은 사용자에게 카카오 로그인 버튼을 표시합니다.
     *
     * @return 시작 화면 (카카오 로그인 버튼)
     * @deprecated 프론트엔드 구현 이후 더 이상 사용되지 않습니다.
     * @since 1.0
     */
    @GetMapping("/home")
    public ResponseEntity<String> goHome() {
        return  ResponseEntity.ok("시작화면 - 카카오 로그인 버튼");
    }

    /**
     * 인증된 사용자의 이름을 반환합니다.
     * 토큰 여부를 확인하여 인증 상태를 검증하는 데 사용됩니다.
     *
     * @param user 인증된 사용자 정보
     * @return 인증된 사용자 정보 또는 401 Unauthorized 상태
     * @deprecated 프론트엔드 구현 이후 더 이상 사용되지 않습니다.
     * @since 1.0
     */
    @GetMapping("/test")
    public ResponseEntity<String> goHome2(@AuthenticationPrincipal CustomUserDetails user) {
        log.info("로그인한 유저: {}", user.getUser());
        if(user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        else return ResponseEntity.ok("로그인되었습니다: "+user.getUser().getName());
    }
}
