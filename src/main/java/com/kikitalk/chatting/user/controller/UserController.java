package com.kikitalk.chatting.user.controller;

import com.kikitalk.chatting.global.response.CommonResponse;
import com.kikitalk.chatting.global.security.jwt.service.CustomUserDetails;
import com.kikitalk.chatting.user.dto.request.signup.SignUpDTO;
import com.kikitalk.chatting.user.dto.response.profile.search.SearchProfileDTO;
import com.kikitalk.chatting.user.dto.response.signup.SignUpFormDTO;
import com.kikitalk.chatting.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


/**
 * 회원 가입 및 인증 관련 컨트롤러입니다. <br>
 * 회원가입을 담당합니다.

 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    /**
     * 입력된 이름과 연락처를 기반으로 회원을 검색하고, 해당 회원의 프로필과 현재 인증된 사용자와의 친구 관계 상태를 반환합니다.
     *
     * @param userDetails 인증된 사용자 정보
     * @param name 검색할 회원의 이름 (옵션)
     * @param phone 검색할 회원의 연락처
     * @return 일치하는 회원의 프로필과 친구 관계 상태, (해당하는 회원이 없으면 <code>null</code>)
     *
     * @throws MethodArgumentNotValidException 유효성 검사에 실패할 경우
     *
     * @since 1.0
     */
    @GetMapping("/search")
    public CommonResponse<SearchProfileDTO> getUserByContact(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                    @RequestParam(name = "name", required = false) String name, @RequestParam(name = "phone") String phone) {
        SearchProfileDTO foundUserProfile = userService.searchUserProfile(userDetails.getUser(), phone);
        return CommonResponse.of("회원 검색이 완료되었습니다.", foundUserProfile);
    }

    /**
     * 로그인 후 사용자의 회원가입 상태를 확인합니다.
     * 사용자의 필수 정보가 모두 입력되었는지 확인하고,
     * null 값이 존재할 경우 회원가입이 완료되지 않았다는 결과를 반환합니다.
     * 클라이언트는 이를 바탕으로 추가 정보를 입력하도록 유도할 수 있습니다.
     *
     * @param userDetails 인증된 사용자 정보
     * @return 회원가입 완료 여부 (true: 완료, false: 미완료)
     *
     * @since 1.0
     */
    @GetMapping("/me/status")
    public CommonResponse<Boolean> getSignUpStatus(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Boolean isCompleted = userService.checkSignUpStatus(userDetails.getUser());
        return CommonResponse.of("회원가입 상태 조회가 완료되었습니다.", isCompleted);
    }

    /**
     * 회원가입 폼에 바인딩할 데이터를 조회합니다.
     * OAuth2에서 전달받아 데이터베이스에 저장한 사용자 정보 중 수정 가능한 데이터만 반환합니다.
     *
     * @param userDetails 인증된 사용자 정보
     * @return 회원가입 폼에 바인딩할 데이터
     *
     * @throws NullPointerException 사용자가 존재하지 않을 경우
     *
     * @since 1.0
     */
    @GetMapping("/me/existing-data")
    public CommonResponse<SignUpFormDTO> getSignUpFormInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        SignUpFormDTO bindingData = userService.getSignUpFormData(userDetails.getUser());
        return CommonResponse.of("회원가입 폼의 바인딩 데이터 조회가 완료되었습니다.", bindingData);
    }

    /**
     * 입력받은 추가 정보를 업데이트하여 사용자의 회원가입을 완료합니다.
     *
     * @param userDetails  인증된 사용자 정보
     * @param info  추가 회원 정보 (이름, 닉네임, 상태 메세지, 연락처)
     * @return HTTP 상태 코드 (200 OK)
     *
     * @throws MethodArgumentNotValidException 유효성 검사에 실패할 경우
     * @throws NullPointerException 사용자가 존재하지 않을 경우
     *
     * @since 1.0
     */
    @PutMapping("/me")
    public CommonResponse<Void> completeSignUp(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody SignUpDTO info) {
        userService.updateRequiredInfo(userDetails.getUser(), info);
        return CommonResponse.of("회원가입이 완료되었습니다.");
    }
}
