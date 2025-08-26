import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import { movieAPI } from '@/api/movie';

export const useMovieStore = defineStore('movie', () => {
	const movieRelease = ref('');
	const cinemas = ref({});

	async function getMovieRelease() {
		const response = await movieAPI.getMovieRelease();
		movieRelease.value = response.data.result;
		console.log(movieRelease.value);
	}
	async function getCinema() {
		const response = await movieAPI.getCinema();
		const _cinemas = response.data.result;

		const grouped = _cinemas.reduce((acc, item) => {
			if (!acc[item.REGION]) acc[item.REGION] = [];
			acc[item.REGION].push(item);
			return acc;
		}, {});

		cinemas.value = grouped; // Pinia 상태에 저장
	}
	function getCinemasByRegion(region) {
		const arr = cinemas.value[region] || [];
		return {
			cinemas: arr,
			count: arr.length,
		};
	}

	return { getMovieRelease, getCinema, movieRelease, getCinemasByRegion };
});
