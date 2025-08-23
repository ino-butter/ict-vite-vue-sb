<template>
	<div class="flex items-center max-w-200 my-5 mx-5 px-6 py-3 bg-white rounded-lg shadow-md">
		<div class="w-[460px] h-full">
			<div>
				<label for="id" class="block w-full mb-2 text-[#364f6b] text-[90%]">이메일</label>
				<input
					id="id"
					type="text"
					v-model="email"
					class="w-full border border-[#dae1e7] shadow-md px-3 py-2 mb-4"
				/>
			</div>
			<div>
				<label for="pw" class="block w-full mb-2 text-[#364f6b] text-[90%]">비밀번호</label>
				<input
					id="pw"
					type="text"
					v-model="pw"
					class="w-full border border-[#dae1e7] shadow-md px-3 py-2 mb-4"
				/>
			</div>
			<button v-on:click="tryLogin"
			class="w-full py-2 px-6 font-bold rounded-sm border-0 border-solid border-[#dae1e7] text-white bg-green-500"
			>
				로그인
			</button>
			<div class="text-xs text-gray-400 text-center w-64 mx-auto p-5 space-x-2">
				<router-link to="/login">로그인</router-link>
				<router-link to="/signup">회원가입</router-link>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref } from 'vue';

const email = ref('');
const pw = ref('');

import { useUserStore } from '@/store/user';
const userStore = useUserStore();

async function tryLogin() {
	await userStore.login(email.value, pw.value);
	console.log("데이터 끝");
}
</script>

<style>

</style>
