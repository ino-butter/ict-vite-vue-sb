<template>
	<div class="flex items-center max-w-200 my-5 mx-5 px-6 py-3 bg-white rounded-lg shadow-md">
		<div class="w-full h-full">
			<div v-for="category in movieCategories" :key="category.id">
				<h2 class="text-2xl mb-4 text-center">{{ category.title }}</h2>

				<div class="overflow-x-auto scrollbar-hide">
					<div class="flex space-x-4 pb-2">
						<div
							class="flex-shrink-0 w-40 text-center"
							v-for="movie in category.movies"
							:key="movie.id"
						>
							<img :src="movie.poster" :alt="movie.title" class="rounded-lg w-full h-auto" />
							<button
								type="submit"
								class="py-1.5 px-6 mt-2 text-sm truncate rounded-sm border-0 border-solid border-[#dae1e7] text-white bg-blue-500"
							>
								예매
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref } from 'vue';
import testImage from '@/img/test.jpg';

const movieCategories = ref([
	{
		id: 1,
		title: '현재 상영 중인 인기 영화',
		movies: [
			{ id: 101, title: '영화 1', poster: testImage },
			{ id: 102, title: '영화 2', poster: testImage },
			{ id: 103, title: '영화 3', poster: testImage },
			{ id: 104, title: '영화 4', poster: testImage },
			{ id: 105, title: '영화 5', poster: testImage },
		],
	},
]);
</script>

<style></style>
