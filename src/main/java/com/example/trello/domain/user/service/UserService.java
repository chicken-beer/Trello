package com.example.trello.domain.user.service;

import com.example.trello.domain.user.dto.UserSignupDto;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(UserSignupDto request) {

        String loginId = request.getLoginId();
        String username = request.getUsername();

        if (userRepository.findByLoginId(loginId).isPresent()) {
            throw new CustomException(HttpStatus.CONFLICT, "중복된 아이디입니다.");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new CustomException(HttpStatus.CONFLICT, "중복된 유저명입니다.");
        }

        String password = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .loginId(loginId)
                .password(password)
                .username(username)
                .description(request.getDescription())
                .build();
        userRepository.save(user);
    }

}
