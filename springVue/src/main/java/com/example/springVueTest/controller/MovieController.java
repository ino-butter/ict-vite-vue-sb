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
	
	@PostMapping("/get_movie_time")
	public Map<String, Object> getMovieTime(@RequestBody Params params) {
		return movieService.getMovieTime(params.getMap());
	}
	
	@PostMapping("/get_seat")
	public Map<String, Object> getSeat(@RequestBody Params params) {
		return movieService.getSeat(params.getMap());
	}
	
	@PostMapping("/reservation_movie")
	public Map<String, Object> reservationMovie(@RequestBody Params params) {
		return movieService.reservationMovie(params.getMap());
	}
	
	@PostMapping("/confirm_reservation_movie")
	public Map<String, Object> confirmReservationMovie(@RequestBody Params params) {
		return movieService.confirmReservationMovie(params.getMap());
	}
}
