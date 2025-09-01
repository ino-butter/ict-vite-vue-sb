<template>
	<Loading></Loading>
	<MovieForm />
</template>

<script setup>
import MovieForm from '@/components/forms/MovieForm.vue';

import { ref, onMounted } from 'vue';
import { useMovieStore } from '@/store/movie';
const movieStore = useMovieStore();

import Loading from '@/views/Loading.vue';
const loading = ref(false);

onMounted(async () => {
	loading.value = true;
	await movieStore.getMovieRelease();
	loading.value = false;
});
</script>

<style></style>
