import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import { movieAPI } from '@/api/movie';

export const useMovieStore = defineStore('movie', () => {
	const regions = ref(['서울', '경기', '인천', '부산', '울산', '경주', '제주', '전라']);
	const movieRelease = ref('');
	const cinemas = ref({});
	const movieTimes = ref();
	const seats = ref();

	const selectedMovieIDX = ref('');
	const selectedRegion = ref('');
	const selectedCinemaIDX = ref('');
	const selectedDate = ref('');
	const selectedSeats = ref([]);
	const selectedSchedule = ref();

	async function getSeat(scheduleIDX) {
		const data = {
			SCHEDULE_IDX: scheduleIDX,
		};
		selectedSchedule.value = scheduleIDX;
		const response = await movieAPI.getSeat(data);
		const flatSeats = response.data.result; // 최상위 배열 안의 배열
		const rows = [];

		flatSeats.forEach(seat => {
			const rowIndex = seat.SEAT_ROW;
			if (!rows[rowIndex]) rows[rowIndex] = [];
			rows[rowIndex].push(seat);
		});
		seats.value = rows;
		console.log(rows);
	}
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
			if (!acc[item.CINEMA_REGION]) acc[item.CINEMA_REGION] = [];
			acc[item.CINEMA_REGION].push(item);
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
					CINEMA_NAME: item.CINEMA_NAME,
					SCHEDULES: [],
				};
			}
			acc[item.CINEMA_IDX].SCHEDULES.push({
				START_TIME: item.START_TIME,
				THEATER_IDX: item.THEATER_IDX,
				IDX: item.IDX,
			});
			return acc;
		}, {});

		// 배열로 변환
		const result = Object.values(grouped);

		// 총 영화 일정 개수
		const totalMovie = arr.length;

		return {
			cinemas: result,
			count: result.length, // 극장(묶음) 수
			totalMovie: totalMovie, // 전체 스케줄 수
		};
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
	async function selectDate(date) {
		selectedDate.value = date;
		const data = {
			MOVIE_IDX: selectedMovieIDX.value,
			CINEMA_IDX: selectedCinemaIDX.value,
			DATE: selectedDate.value,
		};
		const response = await movieAPI.getMovieTime(data);

		// 같은 CINEMA_IDX 기준으로 묶기
		const grouped = response.data.result.reduce((acc, item) => {
			if (!acc[item.THEATER_IDX]) {
				acc[item.THEATER_IDX] = {
					CINEMA_IDX: item.CINEMA_IDX,
					MOVIE_IDX: item.MOVIE_IDX,
					NAME: item.NAME,
					THEATER_IDX: item.THEATER_NAME,
					SCHEDULES: [],
				};
			}
			acc[item.THEATER_IDX].SCHEDULES.push({
				START_TIME: item.START_TIME,
				SCHEDULE_IDX: item.SCHEDULE_IDX,
			});
			return acc;
		}, {});

		// 배열로 변환
		movieTimes.value = Object.values(grouped);
		console.log(movieTimes.value);
	}
	function selectSeat(seat) {
		if (seat.LOCKED === true) return;
		if (seat.RESERVATION_STATUS === 'Reservation') return;
		if (seats.value[seat.SEAT_ROW][seat.SEAT_COL].SEAT_TYPE === 'select') {
			seats.value[seat.SEAT_ROW][seat.SEAT_COL].SEAT_TYPE = 'seat';

			const index = selectedSeats.value.findIndex(s => s.SEAT_IDX === seat.SEAT_IDX);
			if (index !== -1) selectedSeats.value.splice(index, 1);
		} else if (seats.value[seat.SEAT_ROW][seat.SEAT_COL].SEAT_TYPE === 'seat') {
			seats.value[seat.SEAT_ROW][seat.SEAT_COL].SEAT_TYPE = 'select';
			selectedSeats.value.push({
				SEAT_IDX: seat.SEAT_IDX,
			});
		}
		console.log('현재 선택된 좌석 IDX:', selectedSeats.value);
	}
	async function reservationMovie() {
		if (selectedSeats.value.length === 0) {
		} else {
			const data = {
				SCHEDULE_IDX: selectedSchedule.value,
				ACCESS_TOKEN: localStorage.getItem('ACCESS_TOKEN'),
				SELECT_SEAT: selectedSeats.value,
			};
			const response = await movieAPI.reservationMovie(data);
		}
	}
	return {
		regions,
		getMovieRelease,
		getCinema,
		getSeat,
		seats,
		movieRelease,
		reservationMovie,
		movieTimes,
		getCinemas,
		isReleaseMovieDate,
		selectMovie,
		selectRegion,
		selectCinema,
		selectDate,
		selectedRegion,
		selectSeat,
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
});
