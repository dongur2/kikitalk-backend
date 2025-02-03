package com.doni.talk.user.service;

import com.doni.talk.user.domain.User;
import com.doni.talk.user.dto.request.UserSearchDTO;
import com.doni.talk.user.dto.response.SimpleProfileDTO;
import com.doni.talk.user.dto.response.SearchProfileDTO;
import com.doni.talk.user.exception.DuplicateRelationshipRequestException;

import java.util.List;


/**
 * 친구 관련 서비스 인터페이스입니다.
 * 친구 목록 조회, 친구 검색, 친구 추가 기능을 제공합니다.
 *
 * @since 1.0
 */
public interface FriendService {

    /**
     * 회원 ID를 이용하여 해당 회원의 친구 목록을 조회합니다. <br>
     * 조회된 친구들의 표시 이름과 프로필 사진을 포함한 간단한 프로필 목록을 반환합니다.
     *
     * @param id 조회할 회원의 고유 ID
     * @return 친구 목록 (친구가 없을 경우 빈 리스트 <code>List.of()</code>)
     * @since 1.0
     */
    List<SimpleProfileDTO> getFriendList(Long id);

    /**
     * 연락처를 이용하여 해당 연락처를 가진 회원을 조회합니다. <br>
     * 조회된 회원의 표시 이름, 프로필 사진, 상태 메시지를 반환하며, <br>
     * 로그인한 회원과의 친구 관계 여부도 포함됩니다.
     *
     * @param client     현재 로그인한 회원
     * @param searchInfo 검색할 연락처 정보
     * @return 조회된 회원의 프로필 (일치하는 회원이 없을 경우 <code>null</code>)
     * @since 1.0
     */
    SearchProfileDTO getUserBySearch(User client, UserSearchDTO searchInfo);

    /**
     * 회원 고유 ID를 이용하여 친구 추가 요청을 보냅니다. <br>
     * 요청이 성공할 경우 추가된 친구의 프로필 정보를 반환합니다.
     *
     * @param myId    친구 요청을 보내는 회원의 ID
     * @param otherId 친구 요청을 받는 회원의 ID
     * @return 추가된 친구의 프로필 정보
     * @throws NullPointerException 해당 ID를 가진 회원이 존재하지 않을 경우
     * @throws RuntimeException 본인에게 친구 추가 요청을 보낼 경우
     * @throws DuplicateRelationshipRequestException 이미 친구로 추가된 회원에게 친구 추가 요청을 보낼 경우
     * @since 1.0
     */
    SearchProfileDTO addFriend(Long myId, Long otherId);
}
