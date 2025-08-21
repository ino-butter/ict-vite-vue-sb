package com.example.springVueTest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springVueTest.mapper.UserMapper;

@Service
public class UserService {
	
    @Autowired
    private UserMapper userMapper;
	public Map<String, Object> login(Map<String, Object> map) {
		System.out.println(map.toString());
		return userMapper.selectUserByUsername(map);
	}
}