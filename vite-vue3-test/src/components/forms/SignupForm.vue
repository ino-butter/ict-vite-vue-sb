<template>
	<div class="flex items-center max-w-200 my-5 mx-5 px-6 py-3 bg-white rounded-lg shadow-md">
		<form @submit.prevent="submitForm" class="w-[460px] h-full">
			<div>
				<label for="username" class="block w-full mb-2 text-[#364f6b] text-[90%]">이메일</label>
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
            <div>
				<label for="password" class="block w-full mb-2 text-[#364f6b] text-[90%]">닉네임</label>
				<input
					id="password"
					type="text"
					v-model="nickname"
					class="w-full border border-[#dae1e7] shadow-md px-3 py-2 mb-4"
				/>
			</div>
			<button
				type="submit"
				class="w-full py-2 px-6 font-bold rounded-sm border-0 border-solid border-[#dae1e7] text-white bg-green-500"
			>
			회원가입
			</button>
		</form>
	</div>
</template>

<script setup>
import { ref } from 'vue';

const username = ref('');
const password = ref('');
const nickname = ref('');
</script>

<style>

</style>
