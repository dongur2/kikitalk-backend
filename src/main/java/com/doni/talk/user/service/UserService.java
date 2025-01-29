package com.doni.talk.user.service;

import com.doni.talk.user.domain.User;
import com.doni.talk.user.dto.request.UserSearchDTO;
import com.doni.talk.user.dto.request.UserSignUpDTO;
import com.doni.talk.user.dto.request.UserUpdateDTO;
import com.doni.talk.user.dto.response.SimpleProfileDTO;
import com.doni.talk.user.dto.response.UserProfileDTO;
import com.doni.talk.user.dto.response.UserSearchProfileDTO;

import java.util.List;

public interface UserService {
    User getUserBySnsId(String snsId);
    User getUserById(Long id);
    UserProfileDTO getUserProfileById(Long id);

    User join(UserSignUpDTO postInfo);
    String signIn(User user);

    UserProfileDTO updateUserProfile(Long id, UserUpdateDTO updateInfo);

    List<SimpleProfileDTO> getFriendList(Long id);
    UserSearchProfileDTO getUserBySearch(User client, UserSearchDTO searchInfo);
    UserSearchProfileDTO addFriend(Long myId, Long otherId);
}
