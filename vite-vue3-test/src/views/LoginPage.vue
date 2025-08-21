<template>
	<LoginForm />
</template>

<script setup>
import LoginForm from '@/components/forms/LoginForm.vue';

import { onMounted } from 'vue';
import { useCounterStore } from '@/store/counter';
const store = useCounterStore();

onMounted(async () => {
	await store.test();
});
</script>

<style scoped></style>
