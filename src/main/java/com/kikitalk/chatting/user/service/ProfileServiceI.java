package com.kikitalk.chatting.user.service;

import com.kikitalk.chatting.user.domain.User;
import com.kikitalk.chatting.user.dto.request.profile.ProfileUpdateDTO;
import com.kikitalk.chatting.user.dto.response.profile.DetailProfileDTO;
import com.kikitalk.chatting.user.dto.response.profile.SimpleProfileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j @Service
@RequiredArgsConstructor
public class ProfileServiceI implements ProfileService {
    @Autowired private final UserService userService;

    //내 간단 프로필 조회
    @Override
    public SimpleProfileDTO fetchMySimpleProfile(User currentUser) throws NullPointerException {
        User user = userService.getUserById(currentUser.getId());
        return SimpleProfileDTO.from(user);
    }

    //내 상세 프로필 조회
    @Override
    public DetailProfileDTO fetchUserDetailProfile(User currentUser) throws NullPointerException {
        return fetchUserDetailProfile(currentUser.getId());
    }

    //상세 프로필 조회
    @Override
    public DetailProfileDTO fetchUserDetailProfile(Long userId) throws NullPointerException {
        User user = userService.getUserById(userId);
        return DetailProfileDTO.from(user);
    }

    //내 프로필 수정
    @Override @Transactional
    public DetailProfileDTO updateUserProfile(User currentUser, ProfileUpdateDTO newInfo) throws NullPointerException {
        User user = userService.getUserById(currentUser.getId());
        user.updateUserProfile(newInfo);
        return DetailProfileDTO.from(user);
    }
}
