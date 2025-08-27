import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import { movieAPI } from '@/api/movie';

export const useMovieStore = defineStore('movie', () => {
	const movieRelease = ref('');
	const cinemas = ref({});
	const selectMovieIDX = ref('1');

	async function getMovieRelease() {
		const response = await movieAPI.getMovieRelease();
		movieRelease.value = response.data.result;
		console.log(movieRelease.value);
	}
	async function getCinema() {
		if (!selectMovieIDX.value) return;

		const data = {
			MOVIE_IDX: selectMovieIDX.value,
		};
		const response = await movieAPI.getCinema(data);
		const _cinemas = response.data.result;

		const grouped = _cinemas.reduce((acc, item) => {
			if (!acc[item.REGION]) acc[item.REGION] = [];
			acc[item.REGION].push(item);
			return acc;
		}, {});
		cinemas.value = grouped; // Pinia 상태에 저장
		getReleaseMovieDate('', '', '');
	}
	function getCinemas(selectRegion) {
		const arr = cinemas.value[selectRegion] || [];
		return {
			cinemas: arr,
			count: arr.length,
		};
	}
	function getReleaseMovieDate(movieIDX, region, cinemaIDX) {
		return filterMovies(cinemas.value, 1, '부산', 6);
		//return filterMovies(cinemas.value, movieIDX, region, cinemaIDX);
	}
	function isReleaseMovieDate(day) {
		const schedule = filterMovies(cinemas.value, 1, '부산', 6);
		if (!schedule || schedule.length === 0) return false;
		const selectDate = `${day.year}-${day.month}-${day.day}`;
		return schedule.some(item => {
			const startDate = item.START_TIME.split(' ')[0]; // 연월일만 추출
			return startDate === selectDate; // day는 이미 "YYYY-MM-DD"
		});
	}
	return { getMovieRelease, getCinema, movieRelease, getCinemas, isReleaseMovieDate };

	function filterMovies(data, movieIdx, region, cinemaIdx) {
		const result = [];

		for (const [key, arr] of Object.entries(data)) {
			if (region && key !== region) continue; // region이 지정되어 있으면 필터

			arr.forEach(item => {
				if (item.MOVIE_IDX === movieIdx && item.CINEMA_IDX === cinemaIdx) {
					result.push(item);
				}
			});
		}

		return result;
	}
});
