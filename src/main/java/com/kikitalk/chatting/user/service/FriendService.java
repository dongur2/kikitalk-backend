package com.kikitalk.chatting.user.service;

import com.kikitalk.chatting.user.domain.User;
import com.kikitalk.chatting.user.dto.request.FriendRequestDTO;
import com.kikitalk.chatting.user.dto.response.profile.SimpleProfileDTO;
import com.kikitalk.chatting.user.exception.DuplicateRelationshipRequestException;

import java.util.List;


/**
 * 친구 관련 서비스 인터페이스입니다.
 * 친구 목록 조회, 친구 검색, 친구 추가 기능을 제공합니다.
 *
 * @since 1.0
 */
public interface FriendService {

    /**
     * 회원 ID를 이용하여 해당 회원의 친구 목록을 조회합니다.
     * 조회된 친구들의 표시 이름과 프로필 사진을 포함한 간단한 프로필 목록을 반환합니다.
     *
     * @param currentUser 인증된 사용자
     * @return 친구의 간단한 프로필 목록 (친구가 없을 경우 빈 리스트 <code>List.of()</code>)
     *
     * @since 1.0
     */
    List<SimpleProfileDTO> fetchMyFriendSimpleProfileList(User currentUser);


    /**
     * 현재 인증된 사용자와 상대방의 고유 ID를 기반으로 친구 추가 요청을 처리합니다.
     *
     * @param currentUser 인증된 사용자
     * @param requestDTO 상대방의 고유 ID를 포함한 친구 추가 요청 정보
     * @return 친구 추가 성공 여부
     *
     * @throws NullPointerException 해당 ID를 가진 회원이 존재하지 않을 경우
     * @throws RuntimeException 본인에게 친구 추가 요청을 보낼 경우
     * @throws DuplicateRelationshipRequestException 이미 친구로 추가된 회원에게 친구 추가 요청을 보낼 경우
     *
     * @since 1.0
     */
    Boolean addFriend(User currentUser, FriendRequestDTO requestDTO);
}
