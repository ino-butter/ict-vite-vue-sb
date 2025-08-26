<template>
	<div>
		<Loading v-model="loading" />
		<BookTicket></BookTicket>
	</div>
</template>

<script setup>
import BookTicket from '@/components/forms/BookTicketForm.vue';
import Loading from '@/views/Loading.vue';

import { onMounted } from 'vue';
import { useMovieStore } from '@/store/movie';
import { ref } from 'vue';
const movieStore = useMovieStore();
const loading = ref(false);

onMounted(async () => {
	loading.value = true;
	await movieStore.getMovieRelease();
	await movieStore.getCinema();
	loading.value = false;
});
</script>

<style></style>
