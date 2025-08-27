package com.example.springVueTest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springVueTest.service.MovieService;
import com.example.springVueTest.utils.Params;

@RestController
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	private MovieService movieService;

	@PostMapping("/get_movie_release")
	public Map<String, Object> getMovieRelease() {
		return movieService.getMovieRelease();
	}
	
	@PostMapping("/get_cinema")
	public Map<String, Object> getCinema(@RequestBody Params params) {
		return movieService.getCinema(params.getMap());
	}
}
