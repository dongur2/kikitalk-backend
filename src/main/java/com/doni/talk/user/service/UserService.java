package com.doni.talk.user.service;

import com.doni.talk.user.domain.User;
import com.doni.talk.user.dto.request.UserSignUpDTO;
import com.doni.talk.user.dto.request.UserUpdateDTO;
import com.doni.talk.user.dto.response.UserProfileDTO;

public interface UserService {
    User getUserBySnsId(String snsId);
    UserProfileDTO getUserById(Long id);

    User join(UserSignUpDTO postInfo);
    String signIn(User user);

    UserProfileDTO updateUserProfile(Long id, UserUpdateDTO updateInfo);
}
