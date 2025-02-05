package com.kikitalk.chatting.relationship.service;

import com.kikitalk.chatting.relationship.domain.Relationship;
import com.kikitalk.chatting.user.domain.User;
import com.kikitalk.chatting.user.dto.response.SimpleProfileDTO;

import java.util.List;

public interface RelationshipService {
    List<SimpleProfileDTO> getFriendList(Long userId);
    Boolean checkIsFriend(Long userId, Long otherUserId);
    Relationship addRelationship(User user, User other);
}

