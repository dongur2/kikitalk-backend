package com.doni.talk.user.domain;

import com.doni.talk.user.dto.request.UserUpdateDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "tbl_user_info")
@Getter @ToString @NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String snsId; // SNS_id - 로그인 이메일 대체

    @Column(length = 20, unique = true)
    private String phone;

    @Column(length = 20)
    private String name;

    @Column(length = 50)
    private String nickname;

    @Column(length = 100)
    private String message;

    private String profileImage;

    @Builder
    public User(Long id, String snsId, String phone, String name, String nickname, String message, String profileImage) {
        this.id = id;
        this.snsId = snsId;
        this.phone = phone;
        this.name = name;
        this.nickname = nickname;
        this.message = message;
        this.profileImage = profileImage;
    }

    public void updateUserProfile(UserUpdateDTO newInfo, String defaultImg) {
        this.phone = newInfo.getPhone();
        this.nickname = newInfo.getNickname();
        this.message = newInfo.getMessage();
        this.profileImage = (newInfo.getProfileImage() == null) ? defaultImg : newInfo.getProfileImage();
    }
}