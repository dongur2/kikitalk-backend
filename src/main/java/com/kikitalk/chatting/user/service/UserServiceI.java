package com.kikitalk.chatting.user.service;

import com.kikitalk.chatting.global.security.jwt.JwtProvider;
import com.kikitalk.chatting.user.domain.User;
import com.kikitalk.chatting.user.dto.request.signup.UserSaveDTO;
import com.kikitalk.chatting.user.dto.request.signup.SignUpDTO;
import com.kikitalk.chatting.user.dto.response.signup.SignUpFormDTO;
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

    //회원 조회 (oauth2)
    @Override
    public User getUserBySnsId(String snsId) throws NullPointerException {
        return repository.findBySnsId(snsId).orElseThrow(() -> new NullPointerException("해당하는 사용자가 없습니다."));
    }

    //회원 조회
    @Override
    public User getUserById(Long userId) throws NullPointerException {
        return repository.findById(userId).orElseThrow(() -> new NullPointerException("해당하는 사용자가 없습니다."));
    }

    //회원 DB 저장
    @Override @Transactional
    public User save(UserSaveDTO oauth2Info) { return repository.save(oauth2Info.to()); }

    //회원 가입 폼 리다이렉트 여부 확인
    @Override
    public Boolean checkSignUpStatus(User currentUser) {
        return currentUser.getName() != null && currentUser.getNickname() != null && currentUser.getPhone() != null && currentUser.getBirth() != null;
    }

    //회원가입 폼: 바인딩 데이터 조회
    @Override
    public SignUpFormDTO getSignUpFormData(User currentUser) throws NullPointerException {
        User user = getUserById(currentUser.getId());
        return SignUpFormDTO.builder()
                .nickname(user.getNickname())
                .build();
    }

    //회원 가입 폼: 추가 정보 업데이트
    @Override @Transactional
    public void updateRequiredInfo(User currentUser, SignUpDTO additionalInfo) throws NullPointerException {
        User user = getUserById(currentUser.getId());
        user.bindUserProfile(additionalInfo);
    }

    //로그인: 토큰 조회
    @Override
    public String signIn(User currentUser) {
        return jwtProvider.createAccessToken(currentUser);
    }
}
