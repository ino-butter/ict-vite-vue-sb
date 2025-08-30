package com.example.springVueTest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.springVueTest.mapper.AuthMapper;
import com.example.springVueTest.utils.JwtUtil;
import com.example.springVueTest.utils.Params;
import com.example.springVueTest.utils.ResponseParams;

@Service
public class AuthService {

	private final JwtUtil jwtUtil = new JwtUtil();

	@Autowired
	private AuthMapper authMapper;
	
	@Autowired
	private RedisService redisService;

	public Map<String, Object> login(Map<String, Object> map) {
		var searchUser = authMapper.searchUser(map);
		System.out.println("map : " + map);
		System.out.println("userdata : " + searchUser);
		ResponseParams responseParams = new ResponseParams();

		if (searchUser != null) {
			Map<String, Object> urtMap = new HashMap<String, Object>();
			String userIDX = searchUser.get("IDX").toString();
			String accessToken = jwtUtil.generateAccessToken(userIDX);
			String refreshToken = jwtUtil.generateRefreshToken(userIDX);
			System.out.println("로그인 성공");
			System.out.println("access token : " + accessToken);
			System.out.println("refresh token : " + refreshToken);

			urtMap.put("IDX", userIDX);
			urtMap.put("REFRESH_TOKEN", refreshToken);
			urtMap.put("ACCESS_TOKEN", accessToken);
			
			// RefreshToken DB 저장
			//authMapper.updateRefreshToken(urtMap);
			
			// RefreshToken Redis 저장
			redisService.saveRefreshToken(userIDX, refreshToken);

			responseParams.setCode(200).setIsSuccess(true).setData(urtMap);
			
		} else {
			System.out.println("로그인 실패");
			responseParams.setCode(401).setIsSuccess(false);
		}
		return responseParams.getMap();
	}
	public Map<String, Object> autoLogin(Map<String, Object> map) {
		ResponseParams responseParams = new ResponseParams();
		String refreshToken = map.get("REFRESH_TOKEN").toString();
		String userIDX = jwtUtil.extractUsername(refreshToken);
		if (jwtUtil.validateToken(refreshToken, userIDX)){
			Map<String, Object> retmap = new HashMap<String, Object>();		
			String newAccessToken = jwtUtil.generateAccessToken(userIDX);
			retmap.put("ACCESS_TOKEN", newAccessToken);
			responseParams.setCode(200).setIsSuccess(true).setData(retmap);
			System.out.println("Refresh토큰 정상");
		}
		else {
			responseParams.setCode(401).setIsSuccess(false);
			System.out.println("Refresh토큰 만료");
		}

		return responseParams.getMap();
	}
	public Map<String, Object> refreshAccessToken(Map<String, Object> map) {
		System.out.println("AccessToken 재갱신 시작");
		ResponseParams responseParams = new ResponseParams();
		String clientRefreshToken = map.get("REFRESH_TOKEN").toString();
		String refreshToken = map.get("REFRESH_TOKEN").toString();
		String userIDX = jwtUtil.extractUsername(refreshToken);
		if (jwtUtil.validateToken(refreshToken, userIDX)){
			String redisRefreshToken = redisService.getRefreshToken(userIDX);
			if(redisRefreshToken == null) {
				responseParams.setCode(403).setIsSuccess(false).setMessage("RefreshToken 만료 또는 유효하지 않음");
				System.out.println("갱신 실패 - RefreshToken 만료");
				return responseParams.getMap();
			}
			else if (!redisRefreshToken.equals(refreshToken)) {
				responseParams.setCode(403).setIsSuccess(false).setMessage("RefreshToken가 일치하지 않음");
				System.out.println("갱신 실패 - RefreshToken 불일치");
				return responseParams.getMap();
			}else
			{
				Map<String, Object> urtMap = new HashMap<String, Object>();
				String newAccessToken = jwtUtil.generateAccessToken(userIDX);

				urtMap.put("IDX", userIDX);
				urtMap.put("ACCESS_TOKEN", newAccessToken);
				responseParams.setCode(200).setIsSuccess(true).setData(urtMap);
				System.out.println("갱신 성공");
			}
		}
		else {
			responseParams.setCode(403).setIsSuccess(false).setMessage("RefreshToken 만료 또는 유효하지 않음");
			System.out.println("갱신 실패 - RefreshToken 만료");
		}
		return responseParams.getMap();
	}
}