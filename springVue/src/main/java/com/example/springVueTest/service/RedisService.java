package com.example.springVueTest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
	@Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final long REFRESH_TOKEN_EXPIRE = 7; // 7일 유효
    private static final long RESERVATION_MOVIE_EEXPIRE = 5; // 5분 유효

    public void saveRefreshToken(String userIDX, String refreshToken) {
        String key = "REFRESH_TOKEN:" + userIDX;
        redisTemplate.opsForValue().set(key, refreshToken, REFRESH_TOKEN_EXPIRE, TimeUnit.DAYS);
        System.out.println("Redis saveRefreshToken");
    }

    public String getRefreshToken(String userIDX) {
        String key = "REFRESH_TOKEN:" + userIDX;
        System.out.println("Redis getRefreshToken");
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteRefreshToken(String userIDX) {
        String key = "REFRESH_TOKEN:" + userIDX;
        redisTemplate.delete(key);
        System.out.println("Redis deleteRefreshToken");
    }
    
    public void lockSeat(String userIDX, String scheduleIDX, String seatIDX) {
    	String key = "LOCK_SEAT:" + scheduleIDX + ":" + seatIDX;
        redisTemplate.opsForValue().set(key, userIDX, RESERVATION_MOVIE_EEXPIRE, TimeUnit.MINUTES);
    }
    public boolean isSeatLocked(String scheduleIDX, String seatIDX) {
    	String key = "LOCK_SEAT:" + scheduleIDX + ":" + seatIDX;
    	return redisTemplate.hasKey(key);
    }

    public void unlockSeat(String scheduleIDX, String seatIDX) {
    	String key = "LOCK_SEAT:" + scheduleIDX + ":" + seatIDX;
        redisTemplate.delete(key);
        System.out.println("unlock key : " + key);
    }

    
    
    public void userSeat(String userIDX, String scheduleIDX, String seatIDX) {
    	String key = "USER_SEAT:" + scheduleIDX + ":" + userIDX;
    	String existing = redisTemplate.opsForValue().get(key);
        if (existing == null || existing.isEmpty()) {
            redisTemplate.opsForValue().set(key, seatIDX, RESERVATION_MOVIE_EEXPIRE, TimeUnit.MINUTES);
        } else {
            redisTemplate.opsForValue().set(key, existing + "," + seatIDX, RESERVATION_MOVIE_EEXPIRE, TimeUnit.MINUTES);
        }
    }
    public List<String> getUserSeats(String userIDX, String scheduleIDX) {
        String key = "USER_SEAT:" + scheduleIDX + ":" + userIDX;
        String seats = redisTemplate.opsForValue().get(key);
        if (seats == null || seats.isEmpty()) return Collections.emptyList();
        return Arrays.asList(seats.split(","));
    }

    // 결제 시 LOCK 해제 + USER_SEAT 삭제
    public void releaseUserSeats(String userIDX, String scheduleIDX) {
        List<String> seatList = getUserSeats(userIDX, scheduleIDX);
        for (String seatIDX : seatList) {
            unlockSeat(scheduleIDX, seatIDX);
        }
        String key = "USER_SEAT:" + scheduleIDX + ":" + userIDX;
        redisTemplate.delete(key);
        System.out.println("LOCK 해제 : " + key);
    }
    
}
