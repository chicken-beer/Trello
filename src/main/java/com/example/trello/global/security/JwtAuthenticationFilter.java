package com.example.trello.global.security;

import com.example.trello.domain.user.dto.UserLoginDto;
import com.example.trello.global.jwt.JwtUtil;
import com.example.trello.global.redis.RedisRepository;
import com.example.trello.global.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final RedisRepository redisRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, ObjectMapper objectMapper, RedisRepository redisRepository) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.redisRepository = redisRepository;
        setFilterProcessesUrl("/v1/auth/login");
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginDto requestDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginDto.class);

            return getAuthenticationManager()
                    .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                requestDto.getLoginId(),
                                requestDto.getPassword(),
                                null));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String username = userDetails.getUsername();

        String accessToken = jwtUtil.createAccessToken(username);
        String refreshToken = jwtUtil.createRefreshToken(username);

        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);

        redisRepository.setRefreshToken(accessToken.substring(7), refreshToken.substring(7));

        ApiResponse<String> result = ApiResponse.ok("로그인에 성공하셨습니다.");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        
        ApiResponse<String> result = ApiResponse.ok("로그인에 실패하셨습니다.");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}