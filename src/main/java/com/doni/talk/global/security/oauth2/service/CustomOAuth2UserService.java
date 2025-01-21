package com.doni.talk.global.security.oauth2.service;

import com.doni.talk.global.security.oauth2.userInfo.KakaoUserInfo;
import com.doni.talk.global.security.oauth2.userInfo.OAuth2UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j @Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest); //oauth2user 정보
        return processOAuth2User(userRequest, oauth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest request, OAuth2User oauth2User) {
        String accessToken = request.getAccessToken().getTokenValue();

        OAuth2UserInfo oAuth2UserInfo = new KakaoUserInfo(accessToken, oauth2User.getAttributes());
        return new OAuth2UserPrincipal(oAuth2UserInfo);
    }
}
