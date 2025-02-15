package com.kikitalk.chatting.user.controller;

import com.kikitalk.chatting.global.response.CommonResponse;
import com.kikitalk.chatting.global.security.jwt.service.CustomUserDetails;
import com.kikitalk.chatting.user.dto.request.FriendRequestDTO;
import com.kikitalk.chatting.user.dto.response.profile.SimpleProfileDTO;
import com.kikitalk.chatting.user.exception.DuplicateRelationshipRequestException;
import com.kikitalk.chatting.user.service.FriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 친구 관련 컨트롤러입니다.
 * 친구 목록 조회, 친구 추가 기능을 담당합니다.
 *
 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/me/friends")
public class FriendController {
    private final FriendService friendService;

    /**
     * 현재 인증된 사용자의 친구 목록 조회 요청을 처리합니다.
     * 각 친구의 간단한 프로필 정보 목록을 반환하게 됩니다.
     *
     * @param userDetails  인증된 사용자 정보
     * @return 친구의 간단한 프로필 목록 (친구가 없을 경우 빈 리스트 <code>List.of()</code>)
     *
     * @since 1.0
     */
    @GetMapping
    public CommonResponse<List<SimpleProfileDTO>> getMyFriendList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<SimpleProfileDTO> friendList = friendService.fetchMyFriendSimpleProfileList(userDetails.getUser());
        return CommonResponse.of("친구 목록 조회가 완료되었습니다.", friendList);
    }

    /**
     * 인증된 사용자와 상대방의 고유 ID를 기반으로 친구 추가 요청을 처리합니다.
     *
     * @param userDetails 인증된 사용자 정보
     * @param requestDTO 상대방의 고유 ID를 포함한 친구 추가 요청 정보
     * @return 친구 추가 성공 여부
     *
     * @throws NullPointerException 해당 ID를 가진 회원이 존재하지 않을 경우
     * @throws RuntimeException 본인에게 친구 추가 요청을 보낼 경우
     * @throws DuplicateRelationshipRequestException 이미 친구로 추가된 회원에게 친구 추가 요청을 보낼 경우
     *
     * @since 1.0
     */
    @PostMapping
    public CommonResponse<Boolean> requestAddFriend(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody FriendRequestDTO requestDTO) {
        Boolean isFriend = friendService.addFriend(userDetails.getUser(), requestDTO);
        return CommonResponse.of("친구 추가가 완료되었습니다.", isFriend);
    }
}
