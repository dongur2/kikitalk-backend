package com.kikitalk.chatting.user.controller;

import com.kikitalk.chatting.global.response.CommonResponse;
import com.kikitalk.chatting.global.security.jwt.service.CustomUserDetails;
import com.kikitalk.chatting.user.dto.request.SignUpWithInfoDTO;
import com.kikitalk.chatting.user.dto.response.DetailProfileDTO;
import com.kikitalk.chatting.user.service.ProfileService;
import com.kikitalk.chatting.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


/**
 * 회원 가입 및 인증 관련 컨트롤러입니다. <br>
 * 회원가입, 로그인을 담당합니다.

 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final ProfileService profileService;

    /**
     * 회원가입 폼을 반환합니다.
     * 닉네임, 상태 메세지, 연락처를 입력받아 회원가입 요청을 진행할 수 있습니다.
     *
     * @deprecated 프론트엔드 구현 이후 더 이상 사용되지 않습니다.
     * @return 회원가입 폼을 대체하는 임시 안내 문자열
     * @since 1.0
     */
    @GetMapping("/register")
    public CommonResponse<Void> signUpForm() {
        return CommonResponse.of("OAUth2 로그인에 성공했습니다. 추가 회원 정보를 입력하여 회원가입을 진행하세요.");
    }

    @GetMapping
    public CommonResponse<DetailProfileDTO> getUserProfile(@AuthenticationPrincipal CustomUserDetails user) {
        DetailProfileDTO profile = profileService.getUserProfileById(user.getUser().getId());
        return CommonResponse.of("프로필 조회가 완료되었습니다.", profile);
    }

    /**
     * 회원가입을 처리합니다.
     *
     * @param info 회원 정보 (SNS ID, 이름, 닉네임, 상태 메세지, 연락처, 프로필 사진 URL)
     * @return HTTP 상태 코드 (200 OK)
     * @throws MethodArgumentNotValidException 유효성 검사에 실패할 경우
     * @since 1.0
     */
    @PostMapping
    public CommonResponse<Void> signUp(@AuthenticationPrincipal CustomUserDetails user, @Valid @RequestBody SignUpWithInfoDTO info) {
        userService.joinWithInfo(user.getUser(), info);
        return CommonResponse.of("회원가입이 완료되었습니다.");
    }
}
