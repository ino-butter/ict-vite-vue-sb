<template>
	<div v-if="modelValue" class="overlay">
		<div class="spinner"></div>
	</div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
	modelValue: { type: Boolean, required: true },
});

const emit = defineEmits(['update:modelValue']);
</script>

<style scoped>
.overlay {
	position: fixed;
	top: 0;
	left: 0;
	width: 100vw;
	height: 100vh;
	background-color: rgba(0, 0, 0, 0.3); /* 반투명 배경 */
	display: flex;
	justify-content: center;
	align-items: center;
	z-index: 9999;
	overflow: hidden;
}

.spinner {
	width: 60px;
	height: 60px;
	border: 6px solid #f3f3f3;
	border-top: 6px solid #3498db;
	border-radius: 50%;
	animation: spin 1s linear infinite;
}

@keyframes spin {
	0% {
		transform: rotate(0deg);
	}
	100% {
		transform: rotate(360deg);
	}
}
</style>
