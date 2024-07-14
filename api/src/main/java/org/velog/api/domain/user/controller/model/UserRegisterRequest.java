package org.velog.api.domain.user.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @NotBlank(message = "가입 ID를 입력 하세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력 하세요.")
    private String password;

    @NotBlank(message = "이름을 입력 하세요.")
    private String name;

    @Email
    @NotBlank(message = "이메일을 입력 하세요.")
    private String email;
}
