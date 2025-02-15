package com.kikitalk.chatting.user.controller;

import com.kikitalk.chatting.global.response.CommonResponse;
import com.kikitalk.chatting.global.security.jwt.service.CustomUserDetails;
import com.kikitalk.chatting.user.dto.request.UpdateProfileDTO;
import com.kikitalk.chatting.user.dto.response.DetailProfileDTO;
import com.kikitalk.chatting.user.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * 프로필 관련 컨트롤러입니다. <br>
 * 사용자의 프로필 조회 및 수정을 담당합니다.
 *
 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class ProfileController {
    private final ProfileService profileService;

    /**
     * 회원 고유 ID에 해당하는 회원의 상세 프로필을 조회합니다.
     *
     * @param id 조회할 회원의 고유 ID
     * @return 회원 상세 프로필 (이름, 닉네임, 상태 메세지, 프로필 사진)
     * @since 1.0
     */
    @GetMapping("/{id}")
    public CommonResponse<DetailProfileDTO> getUserProfile(@PathVariable("id") Long id) {
        DetailProfileDTO profile = profileService.getUserProfileById(id);
        return CommonResponse.of("프로필 조회가 완료되었습니다.", profile);
    }

    /**
     * 프로필 수정 요청을 처리합니다.
     * <p>수정 가능한 프로필 필드:</p>
     * <ul>
     *     <li>닉네임</li>
     *     <li>연락처</li>
     *     <li>상태 메세지</li>
     *     <li>프로필 사진</li>
     * </ul>
     *
     * @param user     현재 인증된 사용자
     * @param id       수정할 프로필의 회원 고유 ID
     * @param userInfo 수정할 프로필 정보 (닉네임, 연락처, 상태 메시지, 프로필 사진)
     * @return 회원 고유 ID와 수정된 상세 프로필 (이름, 닉네임, 상태 메세지, 프로필 사진)
     * @throws MethodArgumentNotValidException 유효성 검사에 실패할 경우
     * @throws AccessDeniedException 타인의 프로필 수정을 요청할 경우
     * @since 1.0
     */
    @PatchMapping
    public CommonResponse<DetailProfileDTO> updateUserProfile(@AuthenticationPrincipal CustomUserDetails user,
                                                              @Valid @RequestBody UpdateProfileDTO info) {
        DetailProfileDTO updatedProfile = profileService.updateUserProfile(user.getUser().getId(), info);
        return CommonResponse.of("프로필 수정이 완료되었습니다.", updatedProfile);
    }
}
