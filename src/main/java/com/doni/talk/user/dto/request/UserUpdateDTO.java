package com.doni.talk.user.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Setter @Getter @ToString
@AllArgsConstructor @NoArgsConstructor
public class UserUpdateDTO {

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[0-9A-Za-z가-힣]+$", message = "닉네임에 특수문자는 포함할 수 없습니다.")
    @Size(min = 2, max = 20, message = "닉네임은 최소 2글자, 최대 20글자까지 가능합니다.")
    String nickname;

    @Size(max = 50, message = "상태 메세지는 최대 50글자까지 가능합니다.")
    String message;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "휴대폰 번호는 10~11자리의 숫자만 입력할 수 있습니다.")
    String phone;

    String profileImage;
}
