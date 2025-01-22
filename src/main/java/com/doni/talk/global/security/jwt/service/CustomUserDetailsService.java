package com.doni.talk.global.security.jwt.service;

import com.doni.talk.user.domain.User;
import com.doni.talk.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor @Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String snsId) throws UsernameNotFoundException {
        User user = userRepository.findBySnsId(snsId).orElseThrow(() -> new UsernameNotFoundException("SNSID에 해당하는 회원을 찾을 수 없습니다 : " + snsId));
        return new CustomUserDetails(user);
    }
}
