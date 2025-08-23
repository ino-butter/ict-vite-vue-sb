package com.example.springVueTest.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springVueTest.mapper.AuthMapper;
import com.example.springVueTest.utils.JwtUtil;
import com.example.springVueTest.utils.ResponseParams;

@Service
public class UserService {

    public Map<String, Object> test() {
    	ResponseParams responseParams = new ResponseParams();
    	System.out.println("테스트");
        return responseParams.getMap();
    }
}
