<template>
	<div>
		<div class="max-w-6xl mx-auto my-5 bg-white rounded-lg shadow-md overflow-hidden">
			<div class="flex h-[600px]">
				<div class="w-80 border-r border-gray-300 flex flex-col">
					<div class="bg-green-500 text-white font-bold text-center py-2"> 영화 </div>
					<div class="flex-1 overflow-y-auto bg-[#fdfbf5]">
						<ul class="px-3 py-2 text-left text-sm space-y-2">
							<li v-for="(movie, idx) in movieRelease" :key="idx" class="flex items-center gap-2">
								<div
									class="flex items-center gap-2 w-full px-2 py-1 rounded cursor-pointer hover:bg-zinc-400 transition-colors duration-200"
									@click="selectMovie(movie.IDX)"
								>
									<span
										class="text-white text-xs px-1 rounded"
										:class="{
											'bg-green-600': movie.MOVIE_AGE_LIMIT === 'ALL',
											'bg-yellow-500': movie.MOVIE_AGE_LIMIT === '12',
											'bg-orange-600': movie.MOVIE_AGE_LIMIT === '15',
											'bg-red-600': movie.MOVIE_AGE_LIMIT === '18',
										}"
									>
										{{ movie.MOVIE_AGE_LIMIT }}
									</span>
									<span> {{ movie.MOVIE_TITLE }}</span>
								</div>
							</li>
						</ul>
					</div>
				</div>

				<div class="w-30 border-r border-gray-300 flex flex-col">
					<div class="bg-green-500 text-white font-bold text-center py-2"> 지역 </div>
					<div class="flex-1 overflow-y-auto bg-[#fdfbf5]">
						<ul class="px-3 py-2 text-left text-sm space-y-2">
							<li
								v-for="(region, idx) in regions"
								:key="idx"
								class="flex items-center gap-2 w-full px-2 py-1 rounded cursor-pointer hover:bg-zinc-400 transition-colors duration-200"
								@click="selectRegion(region)"
							>
								{{ region }} ({{ getCinemas(region).count }})</li
							>
						</ul>
					</div>
				</div>

				<div class="w-[150px] border-r border-gray-300 flex flex-col">
					<div class="bg-green-500 text-white font-bold text-center py-2"> 극장 </div>
					<div class="flex-1 overflow-y-auto bg-[#fdfbf5]">
						<ul class="px-3 py-2 text-left text-sm space-y-2" v-if="selectedRegion !== ''">
							<li
								v-for="(cinema, idx) in getCinemas(selectedRegion).cinemas"
								:key="idx"
								class="flex items-center gap-2 w-full px-2 py-1 rounded cursor-pointer hover:bg-zinc-400 transition-colors duration-200"
								@click="selectCinema(cinema.CINEMA_IDX)"
							>
								{{ cinema.CINEMA_NAME }}
							</li>
						</ul>
					</div>
				</div>

				<div class="w-40 border-r border-gray-300 flex flex-col">
					<div class="bg-green-500 text-white font-bold text-center py-2"> 날짜 </div>
					<div class="flex-1 overflow-y-auto bg-[#fdfbf5] text-center">
						<p class="font-bold py-2">{{ getToday }}</p>
						<ul class="space-y-1">
							<li v-for="(day, idx) in getDays" :key="idx">
								<div
									:class="[
										'text-center gap-2 w-full px-2 py-1 rounded transition-colors duration-200',
										isReleaseMovieDate(day)
											? [
													day.dayOfWeek === '토'
														? 'text-blue-600 hover:bg-zinc-400 cursor-pointer'
														: day.dayOfWeek === '일'
															? 'text-red-600 hover:bg-zinc-400 cursor-pointer'
															: ' hover:bg-zinc-400 cursor-pointer',
												]
											: 'text-gray-400',
									]"
									@click="selectDate(`${day.year}-${day.month}-${day.day}`)"
								>
									{{ day.dayOfWeek }} {{ day.day }}
								</div>
							</li>
						</ul>
					</div>
				</div>

				<div class="w-[300px] border-r border-gray-300 flex flex-col">
					<div class="bg-green-500 text-white font-bold text-center py-2"> 시간 </div>
					<div
						class="flex-1 overflow-y-auto bg-[#fdfbf5] flex text-left justify-left p-1 text-black"
					>
						<ul class="space-y-4">
							<li v-for="(movieTime, idx) in movieTimes" :key="idx">
								<p class="font-bold text-lg mb-2">{{ movieTime.THEATER_IDX }}</p>
								<div class="flex flex-wrap gap-2">
									<div
										v-for="(time, idx) in movieTime.SCHEDULES"
										:key="idx"
										class="px-3 py-1 bg-gray-200 rounded-md cursor-pointer hover:bg-blue-300 transition"
										@click="selectSchedule(time.SCHEDULE_IDX)"
									>
										{{ formatTime(time.START_TIME) }}
									</div>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div
			v-if="onSeatArea"
			class="max-w-6xl mx-auto my-5 bg-[#fdfbf5] rounded-lg shadow-md border border-gray-300 p-4"
		>
			<!-- 스크린 표시 -->
			<div class="flex justify-center mb-4">
				<div
					class="w-full h-6 bg-gray-700 rounded text-white flex items-center justify-center font-bold"
				>
					SCREEN
				</div>
			</div>

			<!-- 좌석 영역 -->
			<div class="flex flex-col gap-2 overflow-y-auto h-[550px]" ref="seatArea">
				<div v-for="(row, rowIndex) in seats" :key="rowIndex" class="flex gap-2 justify-center">
					<div
						v-for="seat in row"
						:key="seat.SEAT_COL"
						:class="[
							'w-8 h-8 flex items-center justify-center border rounded text-xs font-bold',
							seat.SEAT_TYPE === 'empty'
								? 'bg-gray-100 border-gray-300'
								: seat.SEAT_TYPE === 'screen'
									? 'bg-black text-white border-black'
									: seat.SEAT_TYPE === 'select' ||
										  seat.LOCKED === true ||
										  seat.RESERVATION_STATUS === 'Reservation'
										? 'bg-gray-400 text-white border-gray-400'
										: seat.SEAT_TYPE === 'seat'
											? 'bg-green-300 border-green-400'
											: seat.SEAT_TYPE === 'entrance'
												? 'bg-green-400 border-green-500'
												: seat.SEAT_TYPE === 'exit'
													? 'bg-blue-400 border-blue-500'
													: 'bg-green-300 border-green-400',
						]"
						@click="selectSeat(seat)"
					>
						{{ seat.SEAT_ALIAS }}
					</div>
				</div>
			</div>
			<!-- 레전드 -->
			<div class="flex justify-center gap-4 mt-4 text-xs">
				<div class="flex items-center gap-1"
					><div class="w-4 h-4 bg-green-300 border-green-400 border"></div> 일반석</div
				>
				<div class="flex items-center gap-1"
					><div class="w-4 h-4 bg-yellow-400 border-yellow-500 border"></div> VIP석</div
				>
				<div class="flex items-center gap-1"
					><div class="w-4 h-4 bg-pink-400 border-pink-500 border"></div> 커플석</div
				>
				<div class="flex items-center gap-1"
					><div class="w-4 h-4 bg-gray-100 border-gray-300 border"></div> 빈자리</div
				>
			</div>

			<div class="mt-6 text-center">
				<button
					class="px-6 py-3 bg-green-500 text-white font-bold rounded-lg shadow-md hover:bg-green-600 transition-colors duration-200 text-lg"
					@click="reservationMovie()"
				>
					예매하기
				</button>
			</div>
		</div>
	</div>
</template>
<script setup>
import { useMovieStore } from '@/store/movie';
import { useDateStore } from '@/store/date';
import { storeToRefs } from 'pinia';
import { ref, nextTick } from 'vue';

const movieStore = useMovieStore();
const dateStore = useDateStore();
const { movieRelease, selectedRegion, regions, movieTimes, seats } = storeToRefs(movieStore);

const getCinemas = movieStore.getCinemas;
const getToday = dateStore.getToday;
const getDays = dateStore.getDays;
const getSeat = movieStore.getSeat;
const isReleaseMovieDate = movieStore.isReleaseMovieDate;

const selectMovie = movieStore.selectMovie;
const selectRegion = movieStore.selectRegion;
const selectCinema = movieStore.selectCinema;
const selectDate = movieStore.selectDate;
const selectSeat = movieStore.selectSeat;
const reservationMovie = movieStore.reservationMovie;

const getFilterMovie = movieStore.getFilterMove;

const seatArea = ref(null);
const onSeatArea = ref(false);

function formatTime(startTime) {
	if (!startTime) return '';
	const date = new Date(startTime);
	const hours = String(date.getHours()).padStart(2, '0');
	const minutes = String(date.getMinutes()).padStart(2, '0');
	return `${hours}:${minutes}`;
}
async function selectSchedule(scheduleIDX) {
	onSeatArea.value = true;
	await getSeat(scheduleIDX);
	nextTick(() => {
		if (seatArea.value) {
			seatArea.value.scrollIntoView({ behavior: 'smooth', block: 'center' });
		}
	});
}
</script>
<style></style>
