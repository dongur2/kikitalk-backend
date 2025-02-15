package com.kikitalk.chatting.relationship.service;

import com.kikitalk.chatting.user.domain.User;
import com.kikitalk.chatting.user.exception.DuplicateRelationshipRequestException;

import java.util.List;

/**
 * The interface Relationship service.
 */
public interface RelationshipService {

    /**
     * 인증된 사용자의 모든 친구를 조회하여 친구 목록을 반환합니다.
     *
     * @param currentUser 인증된 사용자
     * @return 사용자의 친구 목록 (친구가 없을 경우 빈 리스트 <code>List.of()</code>)
     */
    List<User> getFriendList(User currentUser);

    /**
     * 인증된 사용자가 특정 상대방을 친구로 추가했는지 여부를 확인합니다.
     *
     * @param userId 인증된 사용자의 고유 ID
     * @param otherUserId 상대방의 고유 ID
     * @return 해당 사용자가 친구라면 true, 아니면 false
     *
     * @since 1.0
     */
    Boolean checkIsFriend(Long userId, Long otherUserId);

    /**
     * 인증된 사용자와 상대방의 친구 관계를 추가합니다.
     * 친구 관계가 이미 존재하는지 확인 후, 없으면 새로운 친구 관계를 추가합니다.
     *
     * @param currentUser 인증된 사용자
     * @param other 상대방
     * @return 친구 관계 추가 성공 여부 (true: 친구 추가 성공, false: 실패)
     *
     * @throws DuplicateRelationshipRequestException 이미 친구 관계인 경우
     *
     * @since 1.0
     */
    Boolean addRelationship(User currentUser, User other);
}

