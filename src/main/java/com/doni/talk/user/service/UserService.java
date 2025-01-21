package com.doni.talk.user.service;

import com.doni.talk.user.domain.User;
import com.doni.talk.user.dto.request.UserSignUpDTO;

public interface UserService {
    User getUserBySnsId(String snsId);
    User join(UserSignUpDTO postInfo);
}
