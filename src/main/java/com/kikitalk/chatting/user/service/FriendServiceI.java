package com.kikitalk.chatting.user.service;

import com.kikitalk.chatting.relationship.domain.Relationship;
import com.kikitalk.chatting.relationship.service.RelationshipService;
import com.kikitalk.chatting.user.domain.User;
import com.kikitalk.chatting.user.dto.request.UserSearchDTO;
import com.kikitalk.chatting.user.dto.response.SimpleProfileDTO;
import com.kikitalk.chatting.user.dto.response.SearchProfileDTO;
import com.kikitalk.chatting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j @Service
@RequiredArgsConstructor
public class FriendServiceI implements FriendService {
    @Autowired private UserService userService;
    @Autowired private UserRepository repository;
    @Autowired private RelationshipService relationshipService;

    @Override
    public List<SimpleProfileDTO> getFriendList(User user) {
        return relationshipService.getFriendList(user.getId());
    }

    @Override
    public SearchProfileDTO getUserBySearch(User user, UserSearchDTO searchInfo) {
        Optional<User> optionalUser = repository.findByPhone(searchInfo.getPhone());

        if (optionalUser.isPresent()) {
            User other = optionalUser.get();
            Boolean isFriend = checkIsFriend(user.getId(), other.getId());
            return SearchProfileDTO.from(other, isFriend);
        }

        return null;
    }

    @Override @Transactional
    public SearchProfileDTO addFriend(User user, Long otherId) throws RuntimeException {
        Long userId = user.getId();
        if(userId.equals(otherId)) throw new RuntimeException("자신을 친구로 추가할 수 없습니다.");

        Relationship relationship = relationshipService.addRelationship(userService.getUserById(userId), userService.getUserById(otherId));
        return SearchProfileDTO.from(relationship.getFriend(), checkIsFriend(userId, otherId));
    }

    private Boolean checkIsFriend(Long userId, Long otherId) {
        return relationshipService.checkIsFriend(userId, otherId);
    }
}
