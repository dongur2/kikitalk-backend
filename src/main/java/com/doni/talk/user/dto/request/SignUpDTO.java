package com.doni.talk.user.dto.request;

import com.doni.talk.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter @Getter @ToString
@AllArgsConstructor @NoArgsConstructor
public class SignUpDTO {
    @NotBlank(message = "오류가 발생했습니다. 다시 소셜 로그인해주세요.")
    String snsId;

    @NotBlank(message = "이름을 입력해주세요.")
    @Pattern(regexp = "^[가-힣]+$", message = "이름은 한글만 가능합니다.")
    @Size(min = 2, max = 10, message = "이름은 최소 2글자, 최대 10글자까지 가능합니다.")
    String name;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[0-9A-Za-z가-힣]+$", message = "닉네임에 특수문자는 포함할 수 없습니다.")
    @Size(min = 2, max = 20, message = "닉네임은 최소 2글자, 최대 20글자까지 가능합니다.")
    String nickname;

    @Size(max = 50, message = "상태 메세지는 최대 50글자까지 가능합니다.")
    String message;

    @NotBlank(message = "연락처를 입력해주세요.")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "연락처는 10~11자리의 숫자만 입력할 수 있습니다.")
    String phone;

    String profileImage;

    public User to(String defaultProfileImage) {
        if(this.profileImage == null) this.profileImage = defaultProfileImage;

        return User.builder()
                .snsId(snsId)
                .name(name)
                .nickname(nickname)
                .message(message)
                .phone(phone)
                .profileImage(profileImage)
                .build();
    }
}
