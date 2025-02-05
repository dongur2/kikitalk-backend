package com.kikitalk.chatting.global.security.oauth2.service;

import com.kikitalk.chatting.global.security.oauth2.userInfo.OAuth2UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class OAuth2UserPrincipal implements OAuth2User, UserDetails {
    private final OAuth2UserInfo userInfo;

    public OAuth2UserPrincipal(OAuth2UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public OAuth2UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() { return userInfo.getId(); }

    @Override
    public Map<String, Object> getAttributes() {
        return userInfo.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getName() { return userInfo.getNickname(); }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
