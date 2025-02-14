package com.kikitalk.chatting.user.controller;

import com.kikitalk.chatting.global.response.CommonResponse;
import com.kikitalk.chatting.global.security.jwt.service.CustomUserDetails;
import com.kikitalk.chatting.user.dto.response.SimpleProfileDTO;
import com.kikitalk.chatting.user.dto.response.SearchProfileDTO;
import com.kikitalk.chatting.user.exception.DuplicateRelationshipRequestException;
import com.kikitalk.chatting.user.service.FriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 친구 관련 컨트롤러입니다. <br>
 * 친구 목록 조회, 친구 검색 및 추가 기능을 담당합니다.
 *
 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
public class FriendController {
    private final FriendService friendService;

    /**
     * 현재 인증된 사용자의 친구 목록 조회 요청을 처리합니다.
     * 각 친구의 간단한 프로필 정보 목록을 반환하게 됩니다.
     *
     * @param user  현재 인증된 사용자
     * @return 친구 목록 (간단한 프로필 정보: 표시 이름, 프로필 사진),
     *         친구가 없을 경우 빈 리스트 <code>List.of()</code>
     * @since 1.0
     */
    @GetMapping
    public CommonResponse<List<SimpleProfileDTO>> getFriends(@AuthenticationPrincipal CustomUserDetails user) {
        List<SimpleProfileDTO> friendList = friendService.getFriendList(user.getUser());
        return CommonResponse.of("친구 목록 조회가 완료되었습니다.", friendList);
    }

    /**
     * 입력받은 연락처와 일치하는 회원 조회 요청을 처리합니다.
     * 연락처와 일치하는 회원이 있을 경우, 회원 프로필과 현재 인증된 사용자와의 친구 관계 상태를 반환합니다.
     *
     * @param user  현재 인증된 사용자
     * @param name  검색 파라미터: 이름
     * @param phone 검색 파라미터: 연락처
     * @return 연락처와 일치하는 회원 프로필 (ID, 표시 이름, 상태 메세지, 프로필 사진, 친구 관계 상태),
     *         일치하는 회원이 없을 경우 <code>null</code>
     * @throws MethodArgumentNotValidException 유효성 검사에 실패할 경우
     * @since 1.0
     */
    @GetMapping("/search")
    public CommonResponse<SearchProfileDTO> searchFriend(@AuthenticationPrincipal CustomUserDetails user,
                                                         @RequestParam(name = "name", required = false) String name, @RequestParam(name = "phone") String phone) {
        SearchProfileDTO foundUserProfile = friendService.getUserBySearch(user.getUser(), phone);
        return CommonResponse.of("회원 검색이 완료되었습니다.", foundUserProfile);
    }

    /**
     * 현재 인증된 사용자와 상대방의 고유 ID를 기반으로 친구 추가 요청을 처리합니다.
     *
     * @param user  현재 인증된 사용자
     * @param otherId 상대방의 고유 ID
     * @return 친구로 추가된 상대방의 프로필 (ID, 표시 이름, 상태 메세지, 프로필 사진, 친구 관계 상태)
     * @throws NullPointerException 해당 ID를 가진 회원이 존재하지 않을 경우
     * @throws RuntimeException 본인에게 친구 추가 요청을 보낼 경우
     * @throws DuplicateRelationshipRequestException 이미 친구로 추가된 회원에게 친구 추가 요청을 보낼 경우
     * @since 1.0
     */
    @PostMapping
    public CommonResponse<SearchProfileDTO> addFriend(@AuthenticationPrincipal CustomUserDetails user, @RequestParam("otherId") Long otherId) {
        SearchProfileDTO addedFriendProfile = friendService.addFriend(user.getUser(), otherId);
        return CommonResponse.of("친구 추가가 완료되었습니다.", addedFriendProfile);
    }
}
