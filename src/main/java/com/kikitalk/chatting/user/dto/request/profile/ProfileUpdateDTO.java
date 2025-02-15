package com.kikitalk.chatting.user.dto.request.profile;

import jakarta.validation.constraints.*;
import lombok.*;

//프로필 수정 요청
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
public class ProfileUpdateDTO {

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[0-9A-Za-z가-힣]{2,20}$", message = "닉네임은 특수문자 없이 2~20글자로 입력해주세요.")
    String nickname;

    @Size(max = 50, message = "상태 메세지는 최대 50글자까지 가능합니다.")
    String message;

    @NotBlank(message = "생년월일을 입력해주세요.")
    @Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", message = "생년월일은 YYYY-MM-DD 형식으로 입력해주세요.")
    String birth;

}
