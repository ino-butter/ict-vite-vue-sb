import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import { movieAPI } from '@/api/movie';

export const useMovieStore = defineStore('movie', () => {
	const regions = ref(['서울', '경기', '인천', '부산', '울산', '경주', '제주', '전라']);
	const movieRelease = ref('');
	const cinemas = ref({});

	const selectedMovieIDX = ref('');
	const selectedRegion = ref('');
	const selectedCinemaIDX = ref('');
	const selectedDate = ref('');

	async function getMovieRelease() {
		const response = await movieAPI.getMovieRelease();
		movieRelease.value = response.data.result;
		console.log(movieRelease.value);
	}
	async function getCinema() {
		if (!selectedMovieIDX.value) return;

		const data = {
			MOVIE_IDX: selectedMovieIDX.value,
		};
		const response = await movieAPI.getCinema(data);
		const _cinemas = response.data.result;

		const grouped = _cinemas.reduce((acc, item) => {
			if (!acc[item.REGION]) acc[item.REGION] = [];
			acc[item.REGION].push(item);
			return acc;
		}, {});
		cinemas.value = grouped; // Pinia 상태에 저장
	}
	function getCinemas(selectRegion) {
		const arr = cinemas.value[selectRegion] || [];

		// 같은 CINEMA_IDX 기준으로 묶기
		const grouped = arr.reduce((acc, item) => {
			if (!acc[item.CINEMA_IDX]) {
				acc[item.CINEMA_IDX] = {
					CINEMA_IDX: item.CINEMA_IDX,
					MOVIE_IDX: item.MOVIE_IDX,
					NAME: item.NAME,
					SCHEDULES: [],
				};
			}
			acc[item.CINEMA_IDX].SCHEDULES.push({
				START_TIME: item.START_TIME,
				THEATER: item.THEATER,
				IDX: item.IDX,
			});
			return acc;
		}, {});

		// 배열로 변환
		const result = Object.values(grouped);

		// 총 영화 일정 개수
		const totalMovie = arr.length;

		console.log(selectRegion, result);
		return {
			cinemas: result,
			count: result.length, // 극장(묶음) 수
			totalMovie: totalMovie, // 전체 스케줄 수
		};
	}
	function getFilterMovie() {
		return filterMoviesDate(
			cinemas.value,
			selectedMovieIDX.value,
			selectedRegion.value,
			selectedCinemaIDX.value,
			selectedDate.value,
		);
	}

	function isReleaseMovieDate(day) {
		const schedule = filterMovies(
			cinemas.value,
			selectedMovieIDX.value,
			selectedRegion.value,
			selectedCinemaIDX.value,
		);
		if (!schedule || schedule.length === 0) return false;
		const selectDate = `${day.year}-${day.month}-${day.day}`;
		return schedule.some(item => {
			const startDate = item.START_TIME.split(' ')[0]; // 연월일만 추출
			return startDate === selectDate; // day는 이미 "YYYY-MM-DD"
		});
	}

	function selectMovie(idx) {
		selectedMovieIDX.value = idx;
		getCinema();
		console.log('selectMovie : ' + idx);
	}
	function selectRegion(region) {
		selectedRegion.value = region;
		console.log('selectRegion : ' + region);
	}
	function selectCinema(idx) {
		selectedCinemaIDX.value = idx;
		console.log('selectCinema : ' + idx);
	}
	function selectDay(date) {
		selectedDate.value = date;
		console.log('selectDay : ' + date);
	}
	return {
		regions,
		getMovieRelease,
		getCinema,
		getFilterMovie,
		movieRelease,
		getCinemas,
		isReleaseMovieDate,
		selectMovie,
		selectRegion,
		selectCinema,
		selectDay,
		selectedRegion,
	};

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
	function filterMoviesDate(data, movieIdx, region, cinemaIdx, date) {
		const result = [];

		for (const [key, arr] of Object.entries(data)) {
			if (region && key !== region) continue; // region이 지정되어 있으면 필터

			arr.forEach(item => {
				const startDate = item.START_TIME.split(' ')[0]; // 연월일만 추출
				if (item.MOVIE_IDX === movieIdx && item.CINEMA_IDX === cinemaIdx && startDate === date) {
					result.push(item);
				}
			});
		}

		return result;
	}
});
