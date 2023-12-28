package com.example.trello.domain.user.dto;

import com.example.trello.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private String loginId;
    private String username;
    private String description;

    @Builder
    public UserResponseDto(String loginId, String username, String description) {
        this.loginId = loginId;
        this.username = username;
        this.description = description;
    }

    public UserResponseDto(User user) {
        this.loginId = user.getLoginId();
        this.username = user.getUsername();
        this.description = user.getDescription();
    }
}
