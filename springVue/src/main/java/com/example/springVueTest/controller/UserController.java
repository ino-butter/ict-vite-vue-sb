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

import com.example.springVueTest.service.UserService;
import com.example.springVueTest.utils.Params;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private  UserService userService;
	
	@PostMapping("/login")
	 public Map<String, Object> login(@RequestBody Params params) {
        return userService.login(params.getMap());
    }
}
