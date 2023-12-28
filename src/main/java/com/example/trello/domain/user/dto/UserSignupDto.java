package com.example.trello.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupDto {

    @NotBlank(message = "아이디 입력하세요.")
    @Pattern(regexp ="^[a-zA-Z0-9]{3,}$", message = "아이디를 확인해주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 4, message = "비밀번호를 4자 이상 입력해주세요.")
    private String password;

    @NotBlank(message = "유저명을 입력하세요.")
    private String username;

    private String description;
}
