import axios from 'axios';
import { useAuthStore } from '@/store/auth';

const api = axios.create({
	baseURL: 'http://localhost:8085', // 여기만 수정하면 됨
	headers: {
		'Content-Type': 'application/json',
	},
});

// 요청 인터셉터: 토큰 자동 포함
api.interceptors.request.use(config => {
	const token = localStorage.getItem('ACCESS_TOKEN');
	const url = config.url || '';
	if (!url.startsWith('/auth')) {
		console.log(url, '| Access Token 사용', token);
		if (token) {
			config.headers.Authorization = `Bearer ${token}`;
		}
	} else {
		console.log(url, '| Access Token 미사용');
	}

	return config;
});

api.interceptors.response.use(
	response => response,
	async error => {
		const originalRequest = error.config;

		if (error.response && error.response.status === 403 && !originalRequest._retry) {
			originalRequest._retry = true;

			// refresh token으로 access token 재발급
			const refreshToken = localStorage.getItem('REFRESH_TOKEN');
			if (refreshToken) {
				try {
					console.error('Access Token 만료, 재갱신 요청');
					const userStore = useAuthStore();
					await userStore.refreshAccessToken();
					// 재요청
					originalRequest.headers.Authorization = `Bearer ${localStorage.getItem('ACCESS_TOKEN')}`;
					return api(originalRequest);
				} catch (error) {
					// refresh token 만료 등
					console.log(error);
					return Promise.reject(refreshError);
				}
			} else {
				// refresh token 없음
				console.error('Refresh token 없음, 로그인 필요');
				return Promise.reject(error);
			}
		}
		return Promise.reject(error);
	},
);

export default api;
