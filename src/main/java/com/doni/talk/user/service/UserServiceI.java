package com.doni.talk.user.service;

import com.doni.talk.global.security.jwt.JwtProvider;
import com.doni.talk.user.domain.User;
import com.doni.talk.user.dto.request.SignUpDTO;
import com.doni.talk.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Slf4j @Service
@RequiredArgsConstructor
public class UserServiceI implements UserService {
    @Autowired private JwtProvider jwtProvider;
    @Autowired private UserRepository repository;

    @Value("${profile.default_img}") private String DEFAULT_PROFILE_IMG;

    //oauth2
    @Override
    public User getUserBySnsId(String snsId) {
        return repository.findBySnsId(snsId).orElseThrow(NullPointerException::new);
    }

    //관계 추가
    @Override
    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow(NullPointerException::new);
    }


    @Override @Transactional
    public User join(SignUpDTO postInfo) {
        return repository.save(postInfo.to(DEFAULT_PROFILE_IMG));
    }

    @Override
    public String signIn(User user) {
        return jwtProvider.createAccessToken(user);
    }
}
