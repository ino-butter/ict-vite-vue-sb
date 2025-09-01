import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import { authAPI } from '@/api/auth';

export const useAuthStore = defineStore('auth', () => {
	const accessToken = ref(localStorage.getItem('ACCESS_TOKEN') || null);
	const refreshToken = ref(localStorage.getItem('REFRESH_TOKEN') || null);

	const isLoging = ref(false);

	async function login(email, pw) {
		try {
			const data = {
				ID: email,
				PW: pw,
			};
			const response = await authAPI.login(data);
			const { isSuccess, result } = response.data;
			if (isSuccess) {
				accessToken.value = result.ACCESS_TOKEN;
				refreshToken.value = result.REFRESH_TOKEN;
				localStorage.setItem('ACCESS_TOKEN', result.ACCESS_TOKEN);
				localStorage.setItem('REFRESH_TOKEN', result.REFRESH_TOKEN);
				console.log('AccessToken을 localStorege에 저장', accessToken.value);
				console.log('RefreshToken localStorege에 저장', refreshToken.value);
				console.log('로그인 성공');
				isLoging.value = true;
				return true;
			} else {
				isLoging.value = false;
				console.log('로그인 실패');
			}
		} catch (error) {
			console.log(error);
		}
		return false;
	}
	async function refreshAccessToken() {
		const data = {
			REFRESH_TOKEN: localStorage.getItem('REFRESH_TOKEN'),
		};
		console.log('재갱신 시작');
		try {
			const response = await authAPI.refreshAccessToken(data);
			const { isSuccess, message, result } = response.data;
			if (isSuccess) {
				localStorage.setItem('ACCESS_TOKEN', result.ACCESS_TOKEN);
				accessToken.value = result.ACCESS_TOKEN;
				console.log('AccessToken 갱신 성공', accessToken.value);
				return true;
			} else {
				isLoging.value = false;
				console.log('AccessToken 갱신 실패', message);
				//localStorage.removeItem('ACCESS_TOKEN');
				//localStorage.removeItem('REFRESH_TOKEN');
			}
		} catch (error) {
			console.log(error);
		}
		return false;
	}
	async function autoLogin() {
		const data = {
			REFRESH_TOKEN: localStorage.getItem('REFRESH_TOKEN'),
		};
		if (data.REFRESH_TOKEN === null) return;
		try {
			const response = await authAPI.autoLogin(data);
			const { isSuccess, message, result } = response.data;
			if (isSuccess) {
				accessToken.value = result.ACCESS_TOKEN;
				localStorage.setItem('ACCESS_TOKEN', result.ACCESS_TOKEN);
				console.log('자동 로그인 성공', accessToken.value);
				isLoging.value = true;
				return true;
			} else {
				isLoging.value = false;
				console.log('자동 로그인 실패', message);
				//localStorage.removeItem('ACCESS_TOKEN');
				//localStorage.removeItem('REFRESH_TOKEN');
			}
		} catch (error) {
			console.log(error);
		}
		return false;
	}
	async function test() {
		try {
			console.log('test API 실행');
			const response = await authAPI.test();
			console.log(response);
		} catch (error) {}
	}

	return { login, refreshAccessToken, autoLogin, test };
});
