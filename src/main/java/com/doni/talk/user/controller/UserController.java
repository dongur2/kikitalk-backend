package com.doni.talk.user.controller;

import com.doni.talk.user.dto.request.SignUpDTO;
import com.doni.talk.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * 회원가입 폼을 반환합니다.
     * 닉네임, 상태 메세지, 연락처를 입력받아 회원가입 요청을 진행할 수 있습니다.
     *
     * @deprecated 프론트엔드 구현 이후 더 이상 사용되지 않습니다.
     * @return 회원가입 폼을 대체하는 임시 안내 문자열
     * @since 1.0
     */
    @GetMapping("/register")
    public ResponseEntity<String> signUpForm() {
        return ResponseEntity.ok("회원가입 폼 - 닉네임, 연락처, 생년월일 입력 후 요청");
    }

    /**
     * 회원가입을 처리합니다.
     *
     * @param userInfo 회원 정보 (SNS ID, 이름, 닉네임, 상태 메세지, 연락처, 프로필 사진 URL)
     * @return HTTP 상태 코드 (200 OK)
     * @throws MethodArgumentNotValidException 유효성 검사에 실패할 경우
     * @since 1.0
     */
    @PostMapping
    public ResponseEntity<HttpStatus> signUp(@Valid @RequestPart("info") SignUpDTO userInfo) {
        userService.join(userInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
