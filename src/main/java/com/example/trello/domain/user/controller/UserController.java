package com.example.trello.domain.user.controller;

import com.example.trello.domain.user.dto.UserPasswordDto;
import com.example.trello.domain.user.dto.UserSignupDto;
import com.example.trello.domain.user.dto.UserProfileDto;
import com.example.trello.domain.user.service.UserService;
import com.example.trello.global.response.ApiResponse;
import com.example.trello.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody UserSignupDto request) {
        userService.signup(request);
        return ResponseEntity.ok( ApiResponse.ok("회원가입에 성공하셨습니다.") );
    }

    @PostMapping("/users/logout")
    public ResponseEntity<ApiResponse> logout(@RequestHeader("Authorization") String token) {
        userService.logout(token);

        return ResponseEntity.ok( ApiResponse.ok("로그아웃에 성공하였습니다.") );
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok( ApiResponse.ok(userService.getProfile(userDetails)) );
    }

    @PatchMapping("/users")
    public ResponseEntity<ApiResponse> updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody UserProfileDto profileDto) {
        userService.updateProfile(userDetails, profileDto);
        return ResponseEntity.ok( ApiResponse.ok("프로필 수정에 성공하셨습니다.") );
    }

    @PatchMapping("/users/password")
    public ResponseEntity<ApiResponse> updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody UserPasswordDto passwordDto) {
        userService.updatePassword(userDetails, passwordDto);
        return ResponseEntity.ok( ApiResponse.ok("비밀번호 변경에 성공하셨습니다.") );
    }

    @DeleteMapping("/users")
    public ResponseEntity<ApiResponse> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUser(userDetails);
        return ResponseEntity.ok( ApiResponse.ok("회원 탈퇴에 성공하셨습니다.") );
    }
}