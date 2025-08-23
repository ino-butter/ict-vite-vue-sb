package com.example.springVueTest.utils;

import com.example.springVueTest.utils.JwtUtil;

import java.util.List;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	public JwtFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 요청 헤더에서 Authorization 추출
		String authHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;

		// Bearer 토큰 확인
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			jwtToken = authHeader.substring(7);

			try {
				username = jwtUtil.extractUsername(jwtToken);
			} catch (Exception e) {
				logger.error("JWT 토큰 파싱 오류: " + e.getMessage());
			}
		}

		// 유저 정보가 있고, 아직 SecurityContext에 인증 안 되어 있을 때만 실행
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			if (jwtUtil.validateToken(jwtToken, username)) {
				// 권한은 지금 예제에서는 비워둠 (DB에서 조회해서 채워도 됨)
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
						List.of());

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// SecurityContext에 인증정보 등록
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		// 다음 필터 진행
		filterChain.doFilter(request, response);
	}
}