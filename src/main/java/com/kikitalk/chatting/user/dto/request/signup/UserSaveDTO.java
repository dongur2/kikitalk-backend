package com.kikitalk.chatting.user.dto.request.signup;

import com.kikitalk.chatting.user.domain.User;
import lombok.*;

//OAuth2 새로운 사용자 DB 저장
@Getter @AllArgsConstructor
public class UserSaveDTO {
    String snsId;
    String nickname;
    String profileImage;

    public User to() {
        return User.builder()
                .snsId(snsId)
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }
}
