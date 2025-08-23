package com.example.springVueTest.mapper;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
	Map<String, Object> searchUser(Map<String,Object> params);
    void updateRefreshToken(Map<String, Object> params);
	Map<String, Object> getUserByRefreshToken(Map<String,Object> params);
}
