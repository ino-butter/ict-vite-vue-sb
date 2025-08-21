<template>
	<div class="flex items-center max-w-200 my-5 mx-5 px-6 py-3 bg-white rounded-lg shadow-md">
		<form @submit.prevent="submitForm" class="w-[460px] h-full">
			<div>
				<label for="username" class="block w-full mb-2 text-[#364f6b] text-[90%]">아이디</label>
				<input
					id="username"
					type="text"
					v-model="username"
					class="w-full border border-[#dae1e7] shadow-md px-3 py-2 mb-4"
				/>
			</div>
			<div>
				<label for="password" class="block w-full mb-2 text-[#364f6b] text-[90%]">비밀번호</label>
				<input
					id="password"
					type="text"
					v-model="password"
					class="w-full border border-[#dae1e7] shadow-md px-3 py-2 mb-4"
				/>
			</div>
			<button
				type="submit"
				class="w-full py-2 px-6 font-bold rounded-sm border-0 border-solid border-[#dae1e7] text-white bg-green-500"
			>
				로그인
			</button>
			<div class="text-xs text-gray-400 text-center w-64 mx-auto p-4">
				<router-link to="/login">로그인</router-link>
				<router-link to="/signup">회원가입</router-link>
			</div>
		</form>
	</div>
</template>

<script setup>
import { ref } from 'vue';

const username = ref('');
const password = ref('');
</script>

<style>
.btn {
	background: #fe9616;
	padding: 0.5rem 1.5rem;
	font-weight: 700;
	border-radius: 0.25rem;
	border: 0 solid #dae1e7;
}
</style>
