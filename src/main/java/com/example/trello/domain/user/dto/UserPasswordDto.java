package com.example.trello.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordDto {

    @NotBlank(message = "현재 비밀번호를 입력하세요.")
    @Size(min = 4, message = "비밀번호를 4자 이상 입력해주세요.")
    private String oldPassword;

    @NotBlank(message = "변경할 비밀번호를 입력하세요.")
    @Size(min = 4, message = "비밀번호를 4자 이상 입력해주세요.")
    private String newPassword;

    @NotBlank(message = "확인용 비밀번호를 입력하세요.")
    @Size(min = 4, message = "비밀번호를 4자 이상 입력해주세요.")
    private String checkPassword;
}
