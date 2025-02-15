package com.kikitalk.chatting.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter @Getter @ToString
@AllArgsConstructor @NoArgsConstructor
public class SignUpWithInfoDTO {
    @NotBlank(message = "이름을 입력해주세요.")
    @Pattern(regexp = "^[가-힣]{2,10}$", message = "이름은 한글로 2~10글자로 입력해주세요.")
    String name;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[0-9A-Za-z가-힣]{2,20}$", message = "닉네임은 특수문자 없이 2~20글자로 입력해주세요.")
    String nickname;

    @NotBlank(message = "생년월일을 입력해주세요.")
    @Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", message = "생년월일은 YYYY-MM-DD 형식으로 입력해주세요.")
    String birth;

    @NotBlank(message = "연락처를 입력해주세요.")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "연락처는 10~11자리의 숫자만 입력할 수 있습니다.")
    String phone;
}