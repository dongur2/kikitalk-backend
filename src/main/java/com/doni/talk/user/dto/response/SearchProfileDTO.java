package com.doni.talk.user.dto.response;

import com.doni.talk.user.domain.User;
import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class SearchProfileDTO {
    Long userId;
    String showName;
    String message;
    String profileImg;
    Boolean isFriend;

    public static SearchProfileDTO from(User user, Boolean isFriend) {
        return SearchProfileDTO.builder()
                .userId(user.getId())
                .showName(user.getNickname() == null ? user.getName() : user.getNickname())
                .message(user.getMessage())
                .profileImg(user.getProfileImage())
                .isFriend(isFriend)
                .build();
    }
}
