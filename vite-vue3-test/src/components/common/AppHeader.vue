<template>
	<header class="flex justify-between items-center py-2.5 px-5 bg-green-500 z-20 shadow-md">
		<div class="space-x-2.5">
			<div v-if="isLoging">
				<router-link
					to="/login"
					class="text-white text-[18px]"
					exact-active-class="text-white font-bold"
					>로그인</router-link
				>

				<router-link
					to="/signup"
					class="text-white text-[18px]"
					exact-active-class="text-white font-bold"
					>회원가입</router-link
				>
			</div>
			<div v-else>
				<router-link to="/" class="text-white text-[18px]" exact-active-class="text-white font-bold"
					>예매내역</router-link
				>
			</div>
		</div>

		<div class="text-white">test</div>
	</header>
</template>

<script setup>
import { useRouter } from 'vue-router';
const router = useRouter();

import { useAuthStore } from '@/store/auth';
const authStore = useAuthStore();

import { storeToRefs } from 'pinia';
const { isLoging } = storeToRefs(authStore);
</script>

<style scoped></style>
