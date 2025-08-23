package com.example.springVueTest.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

	private final String SECRET_STRING = "mySuperSuperSecretKeyForJWTs123456789012"; // 32바이트 이상
	private final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

	private final long ACCESS_TOKEN_EXPIRE = 1000 * 1 * 1; // 밀리 * 초 * 분
	private final long REFRESH_TOKEN_EXPIRE = 1000 * 30 * 1; // 밀리 * 초 * 분
	// private final long REFRESH_TOKEN_EXPIRE = 1000L * 60 * 60 * 24 * 7; // 7일

	// Access Token 생성
	public String generateAccessToken(String username) {
		return createToken(username, ACCESS_TOKEN_EXPIRE);
	}

	// Refresh Token 생성
	public String generateRefreshToken(String username) {
		return createToken(username, REFRESH_TOKEN_EXPIRE);
	}

	private String createToken(String subject, long expireTime) {
		return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expireTime))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}

	public boolean validateToken(String token, String username) {
		try {
	        final String extractedUsername = extractUsername(token);
	        return (extractedUsername.equals(username) && !isTokenExpired(token));
	    } catch (ExpiredJwtException e) {
	        return false; // 만료 → 그냥 false 리턴
	    } catch (Exception e) {
	        return false; // invalid → false 리턴
	    }
	}
}