package com.example.springVueTest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        List<Map<String, Object>> seats = movieMapper.getSeat(map);
        String scheduleIDX = map.get("SCHEDULE_IDX").toString();
        
        for (Map<String, Object> seat : seats) {
        	if(seat.get("SEAT_TYPE").equals("seat"))
        	{
                String seatIDX = seat.get("SEAT_IDX").toString();
                boolean locked = redisService.isSeatLocked(scheduleIDX, seatIDX);
                seat.put("LOCKED", locked);
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
            if(redisService.isSeatLocked(scheduleIDX, seatIDX)){
            	return responseParams.setCode(401).setIsSuccess(false).setMessage("다른사람이 선택한 좌석이 있습니다.").getMap();
            }
            else{
            	
            }
            
        }
    	 List<String> seatIDs = seats.stream()
    		        .map(seat -> seat.get("SEAT_IDX").toString())
    		        .collect(Collectors.toList());
    	 
    	 Map<String, Object> param = new HashMap<>();
    	    param.put("SCHEDULE_IDX", scheduleIDX);
    	    param.put("SEATS", seatIDs);

    	    System.out.println(param);
    	 List<String> alreadyReservedSeats = movieMapper.checkReservedSeats(param);
    	    if (!alreadyReservedSeats.isEmpty()) {
    	        return responseParams
    	            .setCode(401)
    	            .setIsSuccess(false)
    	            .setMessage("이미 예약된 좌석이 있습니다: " + alreadyReservedSeats)
    	            .getMap();
    	    }
    	    System.out.println(alreadyReservedSeats);

    		    System.out.println(seats);
       	for (Map<String, Object> seat : seats) {
            String seatIDX = seat.get("SEAT_IDX").toString();
            redisService.lockSeat(userIDX, scheduleIDX, seatIDX);
            redisService.userSeat(userIDX, scheduleIDX, seatIDX);
       	}
    	responseParams.setCode(201).setIsSuccess(true);
        return responseParams.getMap();
    }
    
    public Map<String, Object> confirmReservationMovie(Map<String, Object> map) {
    	ResponseParams responseParams = new ResponseParams();
    	
		String accessToken = map.get("ACCESS_TOKEN").toString();
		String userIDX = jwtUtil.extractUsername(accessToken);
		String scheduleIDX = map.get("SCHEDULE_IDX").toString(); 
		
		 List<String> seatList = redisService.getUserSeats(userIDX, scheduleIDX);
		 for (String seatIDX : seatList) {
			Map<String, Object> insertMap = new HashMap<String, Object>();
			insertMap.put("SCHEDULE_IDX", scheduleIDX);
			insertMap.put("SEAT_IDX", seatIDX);
			insertMap.put("USER_IDX", userIDX);
			insertMap.put("RESERVATION_STATUS", "reservation");
			movieMapper.reservationMovie(insertMap);
			System.out.println("예약 됨 : " + seatIDX);
		 }
		 redisService.releaseUserSeats(userIDX, scheduleIDX);
		
    	responseParams.setCode(201).setIsSuccess(true);
        return responseParams.getMap();
    }
}
