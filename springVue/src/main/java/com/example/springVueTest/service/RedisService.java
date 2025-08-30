package com.example.springVueTest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
        System.out.println("lock key : " + key);
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
    
    public void unlockSeatByUser(String scheduleIDX, String userIDX) {
        Set<String> keys = redisTemplate.keys("LOCK_SEAT:" + scheduleIDX + ":*");
        if (keys != null) {
            for (String key : keys) {
                String locker = redisTemplate.opsForValue().get(key);
                if (userIDX.equals(locker)) {
                    redisTemplate.delete(key);
                }
            }
        }
    }
}
