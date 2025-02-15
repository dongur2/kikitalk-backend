package com.kikitalk.chatting.user.controller;

import com.kikitalk.chatting.global.response.CommonResponse;
import com.kikitalk.chatting.global.security.jwt.service.CustomUserDetails;
import com.kikitalk.chatting.user.dto.request.profile.ProfileUpdateDTO;
import com.kikitalk.chatting.user.dto.response.profile.DetailProfileDTO;
import com.kikitalk.chatting.user.dto.response.profile.SimpleProfileDTO;
import com.kikitalk.chatting.user.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
     * 로그인한 사용자의 간단한 프로필을 조회합니다.
     *
     * @param userDetails 사용자 인증 정보
     * @return 사용자의 간단한 프로필 정보
     *
     * @throws NullPointerException 사용자 정보가 존재하지 않을 경우
     *
     * @since 1.0
     */
    @GetMapping("/me/simple")
    public CommonResponse<SimpleProfileDTO> getMySimpleProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        SimpleProfileDTO profile = profileService.fetchMySimpleProfile(userDetails.getUser());
        return CommonResponse.of("내 간단 프로필 조회가 완료되었습니다.", profile);
    }

    /**
     * 로그인한 사용자의 상세 프로필을 조회합니다.
     *
     * @param userDetails 사용자 인증 정보
     * @return 사용자의 상세 프로필 정보
     *
     * @throws NullPointerException 사용자 정보가 존재하지 않을 경우
     *
     * @since 1.0
     */
    @GetMapping("/me")
    public CommonResponse<DetailProfileDTO> getMyDetailProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        DetailProfileDTO profile = profileService.fetchUserDetailProfile(userDetails.getUser());
        return CommonResponse.of("내 상세 프로필 조회가 완료되었습니다.", profile);
    }

    /**
     * ID에 해당하는 사용자의 상세 프로필을 조회합니다.
     *
     * @param userId 조회할 사용자의 고유 ID
     * @return 사용자의 상세 프로필 정보
     *
     * @throws NullPointerException 사용자 정보가 존재하지 않을 경우
     *
     * @since 1.0
     */
    @GetMapping("/{userId}")
    public CommonResponse<DetailProfileDTO> getUserDetailProfile(@PathVariable("userId") Long userId) {
        DetailProfileDTO profile = profileService.fetchUserDetailProfile(userId);
        return CommonResponse.of("상대 상세 프로필 조회가 완료되었습니다.", profile);
    }

    /**
     * 로그인한 사용자의 프로필을 수정합니다.
     * <p>수정 가능한 프로필 필드:</p>
     * <ul>
     *     <li>닉네임</li>
     *     <li>연락처</li>
     *     <li>상태 메시지</li>
     *     <li>프로필 사진</li>
     * </ul>
     *
     * @param userDetails 인증된 사용자 정보
     * @param info 수정할 프로필 정보
     * @return 수정된 사용자의 상세 프로필 정보
     *
     * @throws MethodArgumentNotValidException 입력된 데이터에 유효성 검사 오류가 있을 경우
     * @throws NullPointerException 사용자 정보가 존재하지 않을 경우
     *
     * @since 1.0
     */
    @PatchMapping("/me")
    public CommonResponse<DetailProfileDTO> updateMyProfile(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody ProfileUpdateDTO info) {
        DetailProfileDTO updatedProfile = profileService.updateUserProfile(userDetails.getUser(), info);
        return CommonResponse.of("내 프로필 수정이 완료되었습니다.", updatedProfile);
    }
}
