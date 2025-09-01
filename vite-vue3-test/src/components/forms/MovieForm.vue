<template>
	<div class="max-w-6xl mx-auto p-6 text-center bg-green-100">
		<h2 class="text-2xl font-bold mb-6">🎥 현재 상영중인 영화</h2>

		<!-- 영화 목록 그리드 -->
		<div class="grid grid-cols-2 md:grid-cols-4 gap-6 mx-auto w-fit">
			<div
				v-for="movie in movieRelease"
				:key="movie.MOVIE_IDX"
				class="bg-white rounded-xl shadow-md overflow-hidden"
			>
				<!-- 포스터: 2:3 비율 -->
				<div class="w-full aspect-[2/3] overflow-hidden">
					<img
						:src="movie.MOVIE_POSTER_PATH"
						:alt="movie.MOVIE_TITLE"
						class="w-full h-full object-cover"
					/>
				</div>

				<!-- 영화 정보 -->
				<div class="flex-1 flex flex-col p-3 items-center">
					<h3 class="text-lg font-semibold truncate">
						{{ movie.MOVIE_TITLE }}
					</h3>
					<p class="text-sm text-gray-600 flex-1">
						{{ movie.MOVIE_DESCRIPTION || '설명 없음' }}
					</p>

					<!-- 예매 버튼 -->
					<button
						@click="gotoReserve()"
						class="mt-3 bg-green-500 hover:bg-green-600 text-white py-2 px-4 rounded-lg"
					>
						예매하기
					</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { useMovieStore } from '@/store/movie';
const movieStore = useMovieStore();

import { storeToRefs } from 'pinia';
const { movieRelease } = storeToRefs(movieStore);

import { useRouter } from 'vue-router';
const router = useRouter();

function gotoReserve() {
	router.push('/bookticket');
}
</script>

<style></style>
