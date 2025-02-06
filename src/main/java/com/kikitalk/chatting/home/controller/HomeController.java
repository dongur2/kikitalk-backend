package com.kikitalk.chatting.home.controller;

import com.kikitalk.chatting.global.response.CommonResponse;
import com.kikitalk.chatting.global.security.jwt.service.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
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
    public CommonResponse<Void> goHome() {
        return CommonResponse.of("시작화면입니다. 카카오로 시작하기 버튼을 클릭하여 로그인하세요.");
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
    public CommonResponse<Void> goHome2(@AuthenticationPrincipal CustomUserDetails user) {
        return CommonResponse.of("로그인되었습니다. 현재 로그인한 유저의 이름은 " + user.getUser().getName() + "입니다");
    }
}
