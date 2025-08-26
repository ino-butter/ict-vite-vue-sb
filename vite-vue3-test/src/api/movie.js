import api from './index';

export const movieAPI = {
	getMovieRelease() {
		return api.post('/movie/get_movie_release');
	},
	getCinema() {
		return api.post('/movie/get_cinema');
	},
};
