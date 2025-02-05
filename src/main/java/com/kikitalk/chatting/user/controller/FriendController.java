package com.kikitalk.chatting.user.controller;

import com.kikitalk.chatting.global.security.jwt.service.CustomUserDetails;
import com.kikitalk.chatting.user.dto.request.UserSearchDTO;
import com.kikitalk.chatting.user.dto.response.SimpleProfileDTO;
import com.kikitalk.chatting.user.dto.response.SearchProfileDTO;
import com.kikitalk.chatting.user.exception.DuplicateRelationshipRequestException;
import com.kikitalk.chatting.user.service.FriendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1/users")
public class FriendController {
    private final FriendService friendService;

    /**
     * 현재 인증된 사용자의 고유 ID를 기반으로 친구 목록 조회 요청을 처리합니다.
     * 각 친구의 간단한 프로필 정보 목록을 반환하게 됩니다.
     *
     * @param id 현재 인증된 사용자의 고유 ID
     * @return 친구 목록 (간단한 프로필 정보: 표시 이름, 프로필 사진),
     *         친구가 없을 경우 빈 리스트 <code>List.of()</code>
     * @since 1.0
     */
    @GetMapping("/{id}/friends")
    public ResponseEntity<List<SimpleProfileDTO>> getFriends(@PathVariable("id") Long id) {
        return ResponseEntity.ok(friendService.getFriendList(id));
    }

    /**
     * 입력받은 연락처와 일치하는 회원 조회 요청을 처리합니다.
     * 연락처와 일치하는 회원이 있을 경우, 회원 프로필과 현재 인증된 사용자와의 친구 관계 상태를 반환합니다.
     *
     * @param user  현재 인증된 사용자
     * @param param 검색 파라미터 (연락처)
     * @return 연락처와 일치하는 회원 프로필 (ID, 표시 이름, 상태 메세지, 프로필 사진, 친구 관계 상태),
     *         일치하는 회원이 없을 경우 <code>null</code>
     * @throws MethodArgumentNotValidException 유효성 검사에 실패할 경우
     * @since 1.0
     */
    @GetMapping("/search")
    public ResponseEntity<SearchProfileDTO> searchFriend(@AuthenticationPrincipal CustomUserDetails user,
                                                         @Valid @RequestPart(value = "param") UserSearchDTO param) {
        return ResponseEntity.ok(friendService.getUserBySearch(user.getUser(), param));
    }

    /**
     * 현재 인증된 사용자와 상대방의 고유 ID를 기반으로 친구 추가 요청을 처리합니다.
     *
     * @param id      현재 인증된 사용자의 고유 ID
     * @param otherId 상대방의 고유 ID
     * @return 친구로 추가된 상대방의 프로필 (ID, 표시 이름, 상태 메세지, 프로필 사진, 친구 관계 상태)
     * @throws NullPointerException 해당 ID를 가진 회원이 존재하지 않을 경우
     * @throws RuntimeException 본인에게 친구 추가 요청을 보낼 경우
     * @throws DuplicateRelationshipRequestException 이미 친구로 추가된 회원에게 친구 추가 요청을 보낼 경우
     * @since 1.0
     */
    @PostMapping("/{id}/friends")
    public ResponseEntity<SearchProfileDTO> addFriend(@PathVariable("id") Long id, @RequestParam("otherId") Long otherId) {
        return ResponseEntity.ok(friendService.addFriend(id, otherId));
    }
}
