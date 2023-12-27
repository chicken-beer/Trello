package com.example.trello.global.jwt;

import com.example.trello.global.exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j(topic = "Jwt")
public class JwtUtil {

	public static final String BEARER_PREFIX = "Bearer ";
	public static final long ACCESS_TOKEN_TIME  = 1 * 10 * 60 * 1000L;
	public static final String ACCESS_TOKEN_HEADER = "Authorization";
	public static final long REFRESH_TOKEN_TIME  = 7 * 24 * 60 * 60 * 1000L;

	@Value("${jwt.secret.key}")
	private String secretKey;

	private Key key;

	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	@PostConstruct
	public void init() {
		byte[] bytes = Base64.getDecoder().decode(secretKey);
		key = Keys.hmacShaKeyFor(bytes);
	}

	public String resolveToken(HttpServletRequest request, String header) {
		String bearerToken = request.getHeader(header);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(7);
		}
		return null;
	}
	public String createAccessToken(String loginId) {
		return this.createToken(loginId, ACCESS_TOKEN_TIME);
	}

	public String createRefreshToken(String loginId) {
		return this.createToken(loginId, REFRESH_TOKEN_TIME);
	}

	private String createToken(String loginId, long expiration) {
		Date date = new Date();

		return BEARER_PREFIX +
				Jwts.builder()
						.setSubject(loginId)
						.setExpiration(new Date(date.getTime() + expiration))
						.setIssuedAt(date)
						.signWith(key, signatureAlgorithm)
						.compact();
	}

	public Claims getUserInfoFromToken(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		} catch (SecurityException | MalformedJwtException e) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
		} catch (UnsupportedJwtException e) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
		} catch (IllegalArgumentException e) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "JWT claims is empty, 잘못된 JWT 토큰 입니다.");
		}
	}
}