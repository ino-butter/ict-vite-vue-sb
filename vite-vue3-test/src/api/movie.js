import api from './index';

export const movieAPI = {
	getMovieRelease() {
		return api.post('/movie/get_movie_release');
	},
	getCinema(data) {
		return api.post('/movie/get_cinema', data);
	},
	getMovieTime(data) {
		return api.post('/movie/get_movie_time', data);
	},
	getSeat(data) {
		return api.post('/movie/get_seat', data);
	},
	reservationMovie(data) {
		return api.post('/movie/reservation_movie', data);
	},
	confirmReservationMovie(data) {
		return api.post('/movie/confirm_reservation_movie', data);
	},
};
