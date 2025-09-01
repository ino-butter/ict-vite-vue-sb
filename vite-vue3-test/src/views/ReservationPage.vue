<template>
	<div>
		<Loading v-model="loading" />
		<Reservation></Reservation>
	</div>
</template>

<script setup>
import Reservation from '@/components/forms/ReservationForm.vue';
import Loading from '@/views/Loading.vue';

import { onMounted } from 'vue';
import { ref } from 'vue';
const loading = ref(false);

onMounted(async () => {
	loading.value = true;
	loading.value = false;
});
</script>

<style></style>
