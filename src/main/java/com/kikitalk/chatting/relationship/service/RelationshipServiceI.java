package com.kikitalk.chatting.relationship.service;

import com.kikitalk.chatting.relationship.domain.Relationship;
import com.kikitalk.chatting.relationship.repository.RelationshipRepository;
import com.kikitalk.chatting.user.domain.User;
import com.kikitalk.chatting.user.exception.DuplicateRelationshipRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j @Service @AllArgsConstructor
public class RelationshipServiceI implements RelationshipService{
    private final RelationshipRepository repository;

    //친구 목록 조회
    @Override
    public List<User> getFriendList(User currentUser) {
        List<Relationship> relationships = repository.findAllByUserId(currentUser.getId());
        return relationships != null ? extractFriendFromRelationship(relationships) : List.of();
    }

    //친구 추가 여부 확인
    @Override
    public Boolean checkIsFriend(Long userId, Long otherUserId) {
        Optional<Relationship> optionalRelationship = repository.findByUserIdAndFriendId(userId, otherUserId);
        return optionalRelationship.isPresent();
    }

    //친구 추가
    @Override @Transactional
    public Boolean addRelationship(User currentUser, User other) throws DuplicateRelationshipRequestException {
        if(checkIsFriend(currentUser.getId(), other.getId())) throw new DuplicateRelationshipRequestException("이미 친구로 추가된 사용자입니다.");

        try {
            repository.save(Relationship.builder()
                    .user(currentUser)
                    .friend(other)
                    .build());

            return true; //트랜잭션 정상 작동 -> 친구 추가 완료

        } catch (Exception e) { //save()가 정상 작동하지 않았을 경우 -> 친구 추가 실패
            return false;
        }
    }

    //관계 엔티티에서 회원(친구인 상대방) 추출
    private List<User> extractFriendFromRelationship(List<Relationship> relationships) {
        return relationships.stream().map(Relationship::getFriend).toList();
    }
}
