package com.kikitalk.chatting.user.service;

import com.kikitalk.chatting.user.domain.User;
import com.kikitalk.chatting.user.dto.request.UpdateProfileDTO;
import com.kikitalk.chatting.user.dto.response.DetailProfileDTO;
import com.kikitalk.chatting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j @Service
@RequiredArgsConstructor
public class ProfileServiceI implements ProfileService {
    @Autowired private UserRepository repository;
    @Value("${profile.default_img}") private String DEFAULT_PROFILE_IMG;

    @Override
    public DetailProfileDTO getUserProfileById(Long id) throws NullPointerException {
        User user = repository.findById(id).orElseThrow(NullPointerException::new);
        return DetailProfileDTO.from(user);
    }

    @Override @Transactional
    public DetailProfileDTO updateUserProfile(Long id, UpdateProfileDTO updateInfo) {
        User user = repository.findById(id).orElseThrow(NullPointerException::new);
        user.updateUserProfile(updateInfo, DEFAULT_PROFILE_IMG);
        return DetailProfileDTO.from(user);
    }
}
