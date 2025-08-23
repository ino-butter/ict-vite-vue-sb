package com.example.springVueTest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springVueTest.service.AuthService;
import com.example.springVueTest.utils.Params;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody Params params) {
		return authService.login(params.getMap());
	}

	@PostMapping("/refresh_access_token")
	public Map<String, Object> refreshAccessToken(@RequestBody Params params) {
		return authService.refreshAccessToken(params.getMap());
	}
}
