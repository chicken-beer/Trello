package com.example.trello.global.security;

import com.example.trello.global.jwt.JwtUtil;
import com.example.trello.global.redis.RedisRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisRepository redisRepository;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtUtil.resolveToken(request, JwtUtil.ACCESS_TOKEN_HEADER);
        if(StringUtils.hasText(accessToken)) {
            try {
                Claims info = jwtUtil.getUserInfoFromToken(accessToken);
                setAuthentication(info.getSubject());
            } catch (ExpiredJwtException e) {
                if (redisRepository.hasRefreshToken(accessToken)) {
                    String refreshToken = redisRepository.getRefreshToken(accessToken);
                    String loginId = jwtUtil.getUserInfoFromToken(refreshToken).getSubject();
                    String newToken = jwtUtil.createAccessToken(loginId);
                    response.setHeader(JwtUtil.ACCESS_TOKEN_HEADER, newToken);
                    setAuthentication(loginId);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
    public void setAuthentication(String loginId) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(loginId);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String loginId) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}