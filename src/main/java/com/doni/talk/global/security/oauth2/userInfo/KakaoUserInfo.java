package com.doni.talk.global.security.oauth2.userInfo;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;
    private final String id;
    private final String nickname;
    private final String profileImageUrl;
    private final String accessToken;

    public KakaoUserInfo(String accessToken, Map<String, Object> attributes) {
        this.accessToken = accessToken;

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
        this.attributes = kakaoProfile;

        this.id = ((Long) attributes.get("id")).toString();
        this.nickname = (String) kakaoProfile.get("nickname");
        this.profileImageUrl = (String) kakaoProfile.get("profile_image_url");

        this.attributes.put("id", id);
    }

    @Override
    public String getProvider() { return "kakao"; }

    @Override
    public String getAccessToken() { return accessToken; }

    @Override
    public Map<String, Object> getAttributes() { return attributes; }

    @Override
    public String getId() { return id; }

    @Override
    public String getNickname() { return nickname; }

    @Override
    public String getProfileImageUrl() { return profileImageUrl; }
}
