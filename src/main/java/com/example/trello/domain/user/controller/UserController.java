package com.example.trello.domain.user.controller;

import com.example.trello.domain.user.dto.UserSignupDto;
import com.example.trello.domain.user.service.UserService;
import com.example.trello.global.response.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity signup(@Valid @RequestBody UserSignupDto request) {
        userService.signup(request);
        return ResponseEntity.ok(new CustomResponse("회원가입에 성공하셨습니다."));
    }


}
