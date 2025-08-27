package com.example.springVueTest.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springVueTest.mapper.AuthMapper;
import com.example.springVueTest.mapper.MovieMapper;
import com.example.springVueTest.utils.ResponseParams;

@Service
public class MovieService {
	@Autowired
	private MovieMapper movieMapper;
	
    public Map<String, Object> getMovieRelease() {
    	ResponseParams responseParams = new ResponseParams();
    	responseParams.setCode(201).setIsSuccess(true).setData(movieMapper.getMovieRelease());
        return responseParams.getMap();
    }
    
    public Map<String, Object> getCinema(Map<String, Object> map) {
    	ResponseParams responseParams = new ResponseParams();
    	System.out.println(movieMapper.getCinema(map));
    	responseParams.setCode(201).setIsSuccess(true).setData(movieMapper.getCinema(map));
        return responseParams.getMap();
    }
}
