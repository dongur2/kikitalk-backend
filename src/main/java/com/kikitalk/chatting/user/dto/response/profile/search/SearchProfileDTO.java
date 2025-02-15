package com.kikitalk.chatting.user.dto.response.profile.search;

import com.kikitalk.chatting.user.domain.User;
import lombok.*;

//친구 검색 화면에서의 프로필 (검색 결과)
@Getter @Builder @AllArgsConstructor
public class SearchProfileDTO {
    private final Long userId;
    private final String nickname;
    private final String message;
    private final String profileImage;
    private final Boolean isFriend;

    public static SearchProfileDTO from(User user, Boolean isFriend) {
        return SearchProfileDTO.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .message(user.getMessage())
                .profileImage(user.getProfileImage())
                .isFriend(isFriend)
                .build();
    }
}
