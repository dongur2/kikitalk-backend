package com.doni.talk.user.service;

import com.doni.talk.user.domain.User;
import com.doni.talk.user.dto.request.UserSearchDTO;
import com.doni.talk.user.dto.response.SimpleProfileDTO;
import com.doni.talk.user.dto.response.SearchProfileDTO;

import java.util.List;

public interface FriendService {
    List<SimpleProfileDTO> getFriendList(Long id);
    SearchProfileDTO getUserBySearch(User client, UserSearchDTO searchInfo);
    SearchProfileDTO addFriend(Long myId, Long otherId);
}
