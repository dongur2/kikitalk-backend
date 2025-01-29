package com.doni.talk.relationship.service;

import com.doni.talk.relationship.domain.Relationship;
import com.doni.talk.user.domain.User;
import com.doni.talk.user.dto.response.SimpleProfileDTO;

import java.util.List;

public interface RelationshipService {
    List<SimpleProfileDTO> getFriendList(Long userId);
    Boolean checkIsFriend(Long userId, Long otherUserId);
    Relationship addRelationship(User user, User other);
}

