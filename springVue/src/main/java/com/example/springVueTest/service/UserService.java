package com.example.springVueTest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springVueTest.mapper.UserMapper;
import com.example.springVueTest.utils.ResponseParams;

@Service
public class UserService {
	
    @Autowired
    private UserMapper userMapper;
	public Map<String, Object> login(Map<String, Object> map) {
		var data = userMapper.selectUserByUsername(map);
		ResponseParams responseParams = new ResponseParams();
		responseParams.setIsSuccess(true);
		responseParams.setCode(201);
		responseParams.setMessage("성공");
		responseParams.setData(data);
		return responseParams.getMap();
	}
}