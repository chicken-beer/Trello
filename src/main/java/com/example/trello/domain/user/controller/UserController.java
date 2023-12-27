package com.example.trello.domain.user.controller;

import com.example.trello.domain.user.dto.UserSignupDto;
import com.example.trello.domain.user.service.UserService;
import com.example.trello.global.exception.fieldError.FieldErrorDto;
import com.example.trello.global.exception.fieldError.FieldErrorException;
import com.example.trello.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity signup(@Valid @RequestBody UserSignupDto request, BindingResult bindingResult) {
        // validation 검증
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            List<FieldErrorDto> fieldErrorDtoList =fieldErrors.stream().map(FieldErrorDto::new).toList();
            throw new FieldErrorException(
                    "허용된 username 또는 password 값이 아닙니다.",
                    HttpStatus.BAD_REQUEST.value(),
                    fieldErrorDtoList);
        }

        userService.signup(request);
        return ResponseEntity.ok( ApiResponse.ok("회원가입에 성공하셨습니다.") );
    }
}
