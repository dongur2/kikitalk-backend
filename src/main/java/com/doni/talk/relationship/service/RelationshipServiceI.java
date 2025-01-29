package com.doni.talk.relationship.service;

import com.doni.talk.relationship.domain.Relationship;
import com.doni.talk.relationship.repository.RelationshipRepository;
import com.doni.talk.user.domain.User;
import com.doni.talk.user.dto.response.SimpleProfileDTO;
import com.doni.talk.user.exception.DuplicateRelationshipRequestException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j @Service
@AllArgsConstructor @NoArgsConstructor
public class RelationshipServiceI implements RelationshipService{
    @Autowired private RelationshipRepository repository;

    @Override
    public List<SimpleProfileDTO> getFriendList(Long userId) {
        List<Relationship> relationships = repository.findAllByUserId(userId);

        if(relationships != null) return extractFriendFromRelationship(relationships);
        return List.of();
    }

    @Override
    public Boolean checkIsFriend(Long userId, Long otherUserId) {
        Optional<Relationship> optionalRelationship = repository.findByUserIdAndFriendId(userId, otherUserId);
        return optionalRelationship.isPresent();
    }

    @Override @Transactional
    public Relationship addRelationship(User user, User other) {
        if(checkIsFriend(user.getId(), other.getId())) throw new DuplicateRelationshipRequestException("이미 친구로 추가된 사용자입니다.");

        return repository.save(Relationship.builder()
                .user(user)
                .friend(other)
                .build());
    }

    private List<SimpleProfileDTO> extractFriendFromRelationship(List<Relationship> relationships) {
        List<SimpleProfileDTO> list = new ArrayList<>();
        relationships.forEach(relationship ->
        {
            SimpleProfileDTO profile = SimpleProfileDTO.from(relationship.getFriend());
            list.add(profile);
        });
        return list;
    }
}
