package com.kikitalk.chatting.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter @Getter @ToString
@AllArgsConstructor @NoArgsConstructor
public class SignUpWithInfoDTO {
    @NotBlank(message = "이름을 입력해주세요.")
    @Pattern(regexp = "^[가-힣]+$", message = "이름은 한글만 가능합니다.")
    @Size(min = 2, max = 10, message = "이름은 최소 2글자, 최대 10글자까지 가능합니다.")
    String name;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[0-9A-Za-z가-힣]+$", message = "닉네임에 특수문자는 포함할 수 없습니다.")
    @Size(min = 2, max = 20, message = "닉네임은 최소 2글자, 최대 20글자까지 가능합니다.")
    String nickname;

    @NotBlank(message = "생년월일을 입력해주세요.")
    @Pattern(regexp = "^[0-9]{8}$", message = "생년월일은 숫자 8자리로 입력 가능합니다. (YYYYMMDD)")
    String birth;

    @NotBlank(message = "연락처를 입력해주세요.")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "연락처는 10~11자리의 숫자만 입력할 수 있습니다.")
    String phone;
}