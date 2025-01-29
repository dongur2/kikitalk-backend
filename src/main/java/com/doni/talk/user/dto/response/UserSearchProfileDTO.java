package com.doni.talk.user.dto.response;

import com.doni.talk.user.domain.User;
import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class UserSearchProfileDTO {
    Long userId;
    String showName;
    String message;
    String profileImg;
    Boolean isFriend;

    public static UserSearchProfileDTO from(User user, Boolean isFriend) {
        return UserSearchProfileDTO.builder()
                .userId(user.getId())
                .showName(user.getNickname() == null ? user.getName() : user.getNickname())
                .message(user.getMessage())
                .profileImg(user.getProfileImage())
                .isFriend(isFriend)
                .build();
    }
}
