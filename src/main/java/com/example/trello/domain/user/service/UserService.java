package com.example.trello.domain.user.service;

import com.example.trello.domain.user.dto.UserPasswordDto;
import com.example.trello.domain.user.dto.UserProfileDto;
import com.example.trello.domain.user.dto.UserResponseDto;
import com.example.trello.domain.user.dto.UserSignupDto;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.global.exception.CustomException;
import com.example.trello.global.jwt.JwtUtil;
import com.example.trello.global.redis.RedisRepository;
import com.example.trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisRepository redisRepository;

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

    public void logout(String token) {
        String key = token.substring(7);
        if (redisRepository.hasRefreshToken(key)) redisRepository.deleteRefreshToken(key);
    }

    public String reissue(String token) {
        if (StringUtils.hasText(token)) {
            String key = token.substring(7);
            if (redisRepository.hasRefreshToken(key)) {
                String refreshToken = redisRepository.getRefreshToken(key);
                String loginId = jwtUtil.getUserInfoFromToken(refreshToken).getSubject();
                return jwtUtil.createAccessToken(loginId);
            }
        }
        throw new CustomException(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다.");
    }

    public UserResponseDto getProfile(UserDetailsImpl userDetails) {
        return new UserResponseDto(userDetails.getUser());
    }

    @Transactional
    public void updateProfile(UserDetailsImpl userDetails, UserProfileDto updateDto) {
        User user = findUser(userDetails);

        if(updateDto.getUsername()==null) updateDto.setUsername(user.getUsername());
        if(updateDto.getDescription()==null) updateDto.setDescription(user.getDescription());
        user.updateProfile(updateDto);
    }

    @Transactional
    public void updatePassword(UserDetailsImpl userDetails, UserPasswordDto passwordDto) {
        User user = findUser(userDetails);

        String oldPwd = passwordDto.getOldPassword();
        String newPwd = passwordDto.getNewPassword();
        String chkPwd = passwordDto.getCheckPassword();

        if (!passwordEncoder.matches(oldPwd, userDetails.getPassword())) throw new CustomException(HttpStatus.BAD_REQUEST, "현재 비밀번호가 다릅니다.");
        if (!newPwd.equals(chkPwd)) throw new CustomException(HttpStatus.BAD_REQUEST, "변경할 비밀번호와 확인용 비밀번호가 다릅니다.");
        if (oldPwd.equals(newPwd)) throw new CustomException(HttpStatus.BAD_REQUEST, "기존 비밀번호와 변경할 비밀번호가 같습니다.");

        user.updatePassword(passwordEncoder.encode(newPwd));
    }

    public void deleteUser(UserDetailsImpl userDetails) {
        userRepository.delete(userDetails.getUser());
    }

    private User findUser(UserDetailsImpl userDetails) {
        String loginId = userDetails.getUsername();
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."));
    }
}