package com.example.springVueTest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springVueTest.mapper.AuthMapper;
import com.example.springVueTest.mapper.MovieMapper;
import com.example.springVueTest.utils.JwtUtil;
import com.example.springVueTest.utils.ResponseParams;

@Service
public class MovieService {
	@Autowired
	private MovieMapper movieMapper;
	
	@Autowired
	private RedisService redisService;
	
	private final JwtUtil jwtUtil = new JwtUtil();
	
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
    
    public Map<String, Object> getMovieTime(Map<String, Object> map) {
    	ResponseParams responseParams = new ResponseParams();
    	System.out.println(movieMapper.getMovieTime(map));
    	responseParams.setCode(201).setIsSuccess(true).setData(movieMapper.getMovieTime(map));
        return responseParams.getMap();
    }
    
    public Map<String, Object> getSeat(Map<String, Object> map) {
    	ResponseParams responseParams = new ResponseParams();

        List<Map<String, Object>> seats = movieMapper.getSeat(map); // seat 리스트
        String scheduleIDX = map.get("SCHEDULE_IDX").toString();
        
        for (Map<String, Object> seat : seats) {
        	if(seat.get("SEAT_TYPE").equals("seat"))
        	{
                String seatIDX = seat.get("SEAT_IDX").toString();
                boolean locked = redisService.isSeatLocked(scheduleIDX, seatIDX);
                seat.put("LOCKED", locked); // LOCKED 필드 추가
                if(locked)
                	System.out.println("LOCK");
        	}
        }
        System.out.println(seats);

        responseParams.setCode(201).setIsSuccess(true).setData(seats);
        return responseParams.getMap();
    }
    
    public Map<String, Object> reservationMovie(Map<String, Object> map) {
    	ResponseParams responseParams = new ResponseParams();
    	List<Map<String, Object>> seats = (List<Map<String, Object>>) map.get("SELECT_SEAT");
    	System.out.println(map);
		String accessToken = map.get("ACCESS_TOKEN").toString();
		String userIDX = jwtUtil.extractUsername(accessToken);
		String scheduleIDX = map.get("SCHEDULE_IDX").toString(); 
		
    	for (Map<String, Object> seat : seats) {
            String seatIDX = seat.get("SEAT_IDX").toString();
            System.out.println("선택된 좌석 IDX = " + seatIDX);
            redisService.lockSeat(userIDX, scheduleIDX, seatIDX);
        }
    	responseParams.setCode(201).setIsSuccess(true);
        return responseParams.getMap();
    }
}
