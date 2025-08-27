<template>
	<div class="max-w-6xl mx-auto my-5 bg-white rounded-lg shadow-md overflow-hidden">
		<div class="flex h-[600px]">
			<div class="w-80 border-r border-gray-300 flex flex-col">
				<div class="bg-green-500 text-white font-bold text-center py-2"> 영화 </div>
				<div class="flex-1 overflow-y-auto bg-[#fdfbf5]">
					<ul class="px-3 py-2 text-left text-sm space-y-2">
						<li v-for="(movie, idx) in movieRelease" :key="idx" class="flex items-center gap-2">
							<div
								class="flex items-center gap-2 w-full px-2 py-1 rounded cursor-pointer hover:bg-zinc-400 transition-colors duration-200"
							>
								<span
									class="text-white text-xs px-1 rounded"
									:class="{
										'bg-green-600': movie.AGE_LIMIT === 'ALL',
										'bg-yellow-500': movie.AGE_LIMIT === '12',
										'bg-orange-600': movie.AGE_LIMIT === '15',
										'bg-red-600': movie.AGE_LIMIT === '18',
									}"
								>
									{{ movie.AGE_LIMIT }}
								</span>
								<span> {{ movie.TITLE }}</span>
							</div>
						</li>
					</ul>
				</div>
			</div>

			<!-- 지역 -->
			<div class="flex-1 border-r border-gray-300 flex flex-col">
				<div class="bg-green-500 text-white font-bold text-center py-2"> 지역 </div>
				<div class="flex-1 overflow-y-auto bg-[#fdfbf5]">
					<ul class="px-3 py-2 text-left text-sm space-y-2">
						<li
							class="flex items-center gap-2 w-full px-2 py-1 rounded cursor-pointer hover:bg-zinc-400 transition-colors duration-200"
							>서울({{ getCinemas('서울').count }})</li
						>
						<li
							class="flex items-center gap-2 w-full px-2 py-1 rounded cursor-pointer hover:bg-zinc-400 transition-colors duration-200"
							>경기({{ getCinemas('경기').count }})</li
						>
						<li
							class="flex items-center gap-2 w-full px-2 py-1 rounded cursor-pointer hover:bg-zinc-400 transition-colors duration-200"
							>인천({{ getCinemas('인천').count }})</li
						>
						<li
							class="flex items-center gap-2 w-full px-2 py-1 rounded cursor-pointer hover:bg-zinc-400 transition-colors duration-200"
							>부산({{ getCinemas('부산').count }})</li
						>
						<li
							class="flex items-center gap-2 w-full px-2 py-1 rounded cursor-pointer hover:bg-zinc-400 transition-colors duration-200"
							>울산({{ getCinemas('울산').count }})</li
						>
						<li
							class="flex items-center gap-2 w-full px-2 py-1 rounded cursor-pointer hover:bg-zinc-400 transition-colors duration-200"
							>경주({{ getCinemas('경주').count }})</li
						>
						<li
							class="flex items-center gap-2 w-full px-2 py-1 rounded cursor-pointer hover:bg-zinc-400 transition-colors duration-200"
							>제주({{ getCinemas('제주').count }})</li
						>
						<li
							class="flex items-center gap-2 w-full px-2 py-1 rounded cursor-pointer hover:bg-zinc-400 transition-colors duration-200"
							>전라({{ getCinemas('전라').count }})</li
						>
					</ul>
				</div>
			</div>

			<!-- 극장 -->
			<div class="flex-1 border-r border-gray-300 flex flex-col">
				<div class="bg-green-500 text-white font-bold text-center py-2"> 극장 </div>
				<div class="flex-1 overflow-y-auto bg-[#fdfbf5]">
					<ul class="px-3 py-2 text-left text-sm space-y-2" v-if="selectRegion !== ''">
						<li
							v-for="(cinema, idx) in getCinemas('서울').cinemas"
							:key="idx"
							class="flex items-center gap-2 w-full px-2 py-1 rounded cursor-pointer hover:bg-zinc-400 transition-colors duration-200"
						>
							{{ cinema.NAME }}
						</li>
					</ul>
				</div>
			</div>

			<!-- 날짜 -->
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
							>
								{{ day.dayOfWeek }} {{ day.day }}
							</div>
						</li>

						<!-- <li class="text-blue-600">토 22</li> -->
						<!-- <li class="text-red-600">일 23</li> -->
					</ul>
				</div>
			</div>

			<!-- 시간 -->
			<div class="flex-1 flex flex-col">
				<div class="bg-green-500 text-white font-bold text-center py-2"> 시간 </div>
				<div
					class="flex-1 overflow-y-auto bg-[#fdfbf5] text-center flex items-center justify-center text-gray-500"
				>
					영화, 극장, 날짜를 선택해주세요.
				</div>
			</div>
		</div>
	</div>
</template>
<script setup>
import { useMovieStore } from '@/store/movie';
import { useDateStore } from '@/store/date';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';

const movieStore = useMovieStore();
const dateStore = useDateStore();
const { movieRelease, selectRegion } = storeToRefs(movieStore);

const getCinemas = movieStore.getCinemas;
const getToday = dateStore.getToday;
const getDays = dateStore.getDays;
const isReleaseMovieDate = movieStore.isReleaseMovieDate;
</script>
<style></style>
