<template>
	<div class="max-w-lg mx-auto bg-white rounded-2xl shadow-md overflow-hidden">
		<!-- 헤더 -->
		<div class="bg-black text-white text-center py-4 text-xl font-bold"> 🎬 결제 확인 </div>

		<!-- 영화 정보 -->
		<div class="p-6 space-y-4">
			<div>
				<p class="text-sm text-gray-500">영화 제목</p>
				<p class="text-lg font-semibold"> {{ selectedMovie.MOVIE_TITLE }} </p>
			</div>

			<div>
				<p class="text-sm text-gray-500">극장</p>
				<p class="text-lg font-semibold">
					{{ selectedCinema.CINEMA_NAME }} {{ selectedSchedule.THEATER_NAME }}</p
				>
			</div>

			<div>
				<p class="text-sm text-gray-500">상영 시간</p>
				<p class="text-lg font-semibold"> {{ selectedSchedule.START_TIME }}</p>
			</div>

			<div>
				<p class="text-sm text-gray-500">선택 좌석</p>
				<p class="text-lg font-semibold">
					<span v-for="seat in selectedSeats" :key="seat" class="mr-2">
						{{ seat.SEAT_ALIAS }}
					</span>
				</p>
			</div>
		</div>

		<!-- 버튼 -->
		<div class="flex justify-between border-t p-4">
			<button class="px-4 py-2 rounded-lg bg-gray-200 hover:bg-gray-300 transition" @click="goBack">
				뒤로가기
			</button>
			<button
				class="px-4 py-2 rounded-lg bg-green-500 text-white hover:bg-green-600 transition"
				@click="confirmReservationMovie"
			>
				결제하기
			</button>
		</div>
	</div>
</template>
<script setup>
import { movieAPI } from '@/api/movie';
import { useMovieStore } from '@/store/movie';
import { storeToRefs } from 'pinia';

import { useRouter } from 'vue-router';
const router = useRouter();

const movieStore = useMovieStore();

const { selectedMovie, selectedCinema, selectedSchedule, selectedSeats, movieTimes } =
	storeToRefs(movieStore);

async function confirmReservationMovie() {
	const data = {
		SCHEDULE_IDX: selectedSchedule.value.SCHEDULE_IDX,
		ACCESS_TOKEN: localStorage.getItem('ACCESS_TOKEN'),
	};
	const response = await movieAPI.confirmReservationMovie(data);
	if (response.data.isSuccess === true) {
		alert('예매 완료!');
		router.push('/main');
	}
}
</script>

<style></style>
