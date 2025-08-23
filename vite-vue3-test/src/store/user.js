import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import { userAPI } from '@/api/user';

export const useUserStore = defineStore('user', () => {
	const accessToken = ref(localStorage.getItem('ACCESS_TOKEN') || null);
	const refreshToken = ref(localStorage.getItem('REFRESH_TOKEN') || null);

	async function login(email, pw) {
		try {
			const data = {
				ID: email,
				PW: pw,
			};
			const response = await userAPI.login(data);
			const { isSuccess, result } = response.data;
			if (isSuccess) {
				accessToken.value = result.ACCESS_TOKEN;
				refreshToken.value = result.REFRESH_TOKEN;
				localStorage.setItem('ACCESS_TOKEN', result.ACCESS_TOKEN);
				localStorage.setItem('REFRESH_TOKEN', result.REFRESH_TOKEN);
				console.log('AccessToken을 localStorege에 저장', accessToken.value);
				console.log('RefreshToken localStorege에 저장', refreshToken.value);
				console.log('로그인 성공');
			} else {
				console.log('로그인 실패');
			}
		} catch (error) {
			console.log(error);
		}
	}
	async function refreshAccessToken() {
		const data = {
			REFRESH_TOKEN: localStorage.getItem('REFRESH_TOKEN'),
		};
		console.log('재갱신 시작');
		const response = await userAPI.refreshAccessToken(data);
		const { isSuccess, message, result } = response.data;
		if (isSuccess) {
			accessToken.value = result.ACCESS_TOKEN;
			localStorage.setItem('ACCESS_TOKEN', result.ACCESS_TOKEN);
			console.log('갱신 성공', accessToken.value);
		} else {
			console.log('갱신 실패', message);
		}
	}
	async function test() {
		try {
			console.log('test API 실행');
			const response = await userAPI.test();
			console.log(response);
		} catch (error) {}
	}

	return { login, refreshAccessToken, test };
});
