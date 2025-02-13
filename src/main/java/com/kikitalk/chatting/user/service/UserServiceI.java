package com.kikitalk.chatting.user.service;

import com.kikitalk.chatting.global.security.jwt.JwtProvider;
import com.kikitalk.chatting.user.domain.User;
import com.kikitalk.chatting.user.dto.request.SignUpDTO;
import com.kikitalk.chatting.user.dto.request.SignUpWithInfoDTO;
import com.kikitalk.chatting.user.repository.UserRepository;
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


    //DB 회원 가입
    @Override @Transactional
    public User join(SignUpDTO postInfo) { return repository.save(postInfo.to()); }

    //회원 가입 폼 업데이트
    @Override @Transactional
    public void joinWithInfo(User user, SignUpWithInfoDTO updateInfo) {
        User found = repository.findById(user.getId()).orElseThrow(NullPointerException::new);
        found.bindUserProfile(updateInfo);
    }

    @Override
    public String signIn(User user) {
        return jwtProvider.createAccessToken(user);
    }
}
