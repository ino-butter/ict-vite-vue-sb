package com.example.springVueTest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

	public Map<String, Object> login(Map<String, Object> map) {
		var searchUser = authMapper.searchUser(map);
		System.out.println("map : " + map);
		System.out.println("userdata : " + searchUser);
		ResponseParams responseParams = new ResponseParams();

		if (searchUser != null) {
			Map<String, Object> urtMap = new HashMap<String, Object>();
			String idx = searchUser.get("IDX").toString();
			String accessToken = jwtUtil.generateAccessToken(idx);
			String refreshToken = jwtUtil.generateRefreshToken(idx);
			System.out.println("로그인 성공");
			System.out.println("access token : " + accessToken);
			System.out.println("refresh token : " + refreshToken);

			urtMap.put("IDX", idx);
			urtMap.put("REFRESH_TOKEN", refreshToken);
			urtMap.put("ACCESS_TOKEN", accessToken);
			authMapper.updateRefreshToken(urtMap);
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
		String idx = jwtUtil.extractUsername(refreshToken);
		if (jwtUtil.validateToken(refreshToken, idx)){
			Map<String, Object> retmap = new HashMap<String, Object>();		
			String newAccessToken = jwtUtil.generateAccessToken(idx);
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
		Map<String, Object> getUserByRefreshToken = authMapper.getUserByRefreshToken(map);
		if (getUserByRefreshToken != null) {
			
			String idx = getUserByRefreshToken.get("IDX").toString();
			if (!jwtUtil.validateToken(clientRefreshToken, idx)) {
				responseParams.setCode(403).setIsSuccess(false).setMessage("RefreshToken 만료 또는 유효하지 않음");
				System.out.println("갱신 실패 - RefreshToken 만료");
				return responseParams.getMap();
			}
			
			
			Map<String, Object> urtMap = new HashMap<String, Object>();
			String newAccessToken = jwtUtil.generateAccessToken(idx);

			urtMap.put("IDX", idx);
			urtMap.put("ACCESS_TOKEN", newAccessToken);
			responseParams.setCode(200).setIsSuccess(true).setData(urtMap);
			System.out.println("갱신 성공");
		} else {
			responseParams.setCode(400).setIsSuccess(false).setMessage("RefreshToken이 없거나 불일치");
			System.out.println("갱신 실패");
		}
		return responseParams.getMap();
	}
}