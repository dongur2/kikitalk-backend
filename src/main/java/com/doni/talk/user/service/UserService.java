package com.doni.talk.user.service;

import com.doni.talk.user.domain.User;
import com.doni.talk.user.dto.request.SignUpDTO;

public interface UserService {
    User getUserBySnsId(String snsId);
    User getUserById(Long id);

    User join(SignUpDTO postInfo);
    String signIn(User user);
}
