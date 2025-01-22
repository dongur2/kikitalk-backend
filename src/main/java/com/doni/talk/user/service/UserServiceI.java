package com.doni.talk.user.service;

import com.doni.talk.global.security.jwt.JwtProvider;
import com.doni.talk.user.domain.User;
import com.doni.talk.user.dto.request.UserSignUpDTO;
import com.doni.talk.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j @Service @AllArgsConstructor
public class UserServiceI implements UserService {
    @Autowired private final JwtProvider jwtProvider;
    @Autowired private final UserRepository repository;

    @Override
    public User getUserBySnsId(String snsId) {
        Optional<User> user = repository.findBySnsId(snsId);
        return user.orElse(null);
    }

    @Override @Transactional
    public User join(UserSignUpDTO postInfo) {
        return repository.save(postInfo.to());
    }

    @Override
    public String signIn(User user) {
        log.info("로그인합니다. {}", user);
        return jwtProvider.createAccessToken(user);
    }
}
