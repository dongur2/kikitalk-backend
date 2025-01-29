package com.doni.talk.user.dto.response;

import com.doni.talk.user.domain.User;
import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class SimpleProfileDTO {
    String profileName;
    String profileImg;

    public static SimpleProfileDTO from(User user) {
        String showName = user.getNickname() == null ? user.getName() : user.getNickname();

        return SimpleProfileDTO.builder()
                .profileName(showName)
                .profileImg(user.getProfileImage())
                .build();
    }
}
