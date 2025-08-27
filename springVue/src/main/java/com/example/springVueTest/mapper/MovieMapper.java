package com.example.springVueTest.mapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MovieMapper {
	List<Map<String, Object>> getMovieRelease();
	List<Map<String, Object>> getCinema(Map<String, Object> params);
}
