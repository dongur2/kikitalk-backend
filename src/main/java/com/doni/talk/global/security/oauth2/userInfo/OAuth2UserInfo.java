package com.doni.talk.global.security.oauth2.userInfo;

import java.util.Map;

public interface OAuth2UserInfo {
    String getProvider();
    String getAccessToken();
    Map<String, Object> getAttributes();

    String getId();
    String getNickname();
    String getProfileImageUrl();
}
