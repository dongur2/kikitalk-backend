package com.doni.talk.user.service;

import com.doni.talk.global.security.jwt.JwtProvider;
import com.doni.talk.user.domain.User;
import com.doni.talk.user.dto.request.UserSignUpDTO;
import com.doni.talk.user.dto.request.UserUpdateDTO;
import com.doni.talk.user.dto.response.UserProfileDTO;
import com.doni.talk.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j @Service
@AllArgsConstructor @NoArgsConstructor
public class UserServiceI implements UserService {
    @Autowired private JwtProvider jwtProvider;
    @Autowired private UserRepository repository;

    @Value("${profile.default_img}") private String DEFAULT_PROFILE_IMG;

    @Override
    public User getUserBySnsId(String snsId) {
        return repository.findBySnsId(snsId).orElseThrow(NullPointerException::new);
    }

    @Override
    public UserProfileDTO getUserById(Long id) throws NullPointerException {
        User user = repository.findById(id).orElseThrow(NullPointerException::new);
        return UserProfileDTO.from(user);
    }

    @Override @Transactional
    public User join(UserSignUpDTO postInfo) {
        return repository.save(postInfo.to(DEFAULT_PROFILE_IMG));
    }

    @Override
    public String signIn(User user) {
        return jwtProvider.createAccessToken(user);
    }

    @Override @Transactional
    public UserProfileDTO updateUserProfile(Long id, UserUpdateDTO updateInfo) {
        User user = repository.findById(id).orElseThrow(NullPointerException::new);
        user.updateUserProfile(updateInfo, DEFAULT_PROFILE_IMG);
        return UserProfileDTO.from(user);
    }
}
