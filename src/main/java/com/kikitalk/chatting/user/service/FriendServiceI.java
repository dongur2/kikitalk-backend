package com.kikitalk.chatting.user.service;

import com.kikitalk.chatting.relationship.service.RelationshipService;
import com.kikitalk.chatting.user.domain.User;
import com.kikitalk.chatting.user.dto.request.FriendRequestDTO;
import com.kikitalk.chatting.user.dto.response.profile.SimpleProfileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j @Service @RequiredArgsConstructor
public class FriendServiceI implements FriendService {
    @Autowired private final UserService userService;
    @Autowired private final RelationshipService relationshipService;

    //친구 목록 조회
    @Override
    public List<SimpleProfileDTO> fetchMyFriendSimpleProfileList(User currentUser) {
        List<User> friends = relationshipService.getFriendList(currentUser);
        return convertToSimpleProfile(friends);
    }

    //친구 추가
    @Override @Transactional
    public Boolean addFriend(User currentUser, FriendRequestDTO requestDTO) throws RuntimeException {
        Long userId = currentUser.getId();
        Long otherId = requestDTO.getOtherId();
        if(userId.equals(otherId)) throw new RuntimeException("자신을 친구로 추가할 수 없습니다.");

        //추가 후 성공 여부 반환
        return relationshipService.addRelationship(userService.getUserById(userId), userService.getUserById(otherId));
    }

    //친구 목록을 간단한 프로필 목록으로 변환
    private List<SimpleProfileDTO> convertToSimpleProfile(List<User> friends) {
        if(friends.isEmpty()) return List.of();

        return friends.stream()
                .map(SimpleProfileDTO::from)
                .toList();
    }
}
