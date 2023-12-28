package com.example.trello.domain.user.entity;

import com.example.trello.domain.user.dto.UserProfileDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String description;

    @Builder
    public User(String loginId, String password, String username, String description) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.description = description;
    }

    public void updateProfile(UserProfileDto updateDto) {
        this.username = updateDto.getUsername();
        this.description = updateDto.getDescription();
    }

    public void updatePassword(String newPwd) {
        this.password = newPwd;
    }
}
