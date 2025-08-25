import api from './index';

export const authAPI = {
	login(data) {
		return api.post('/auth/login', data);
	},
	refreshAccessToken(data) {
		return api.post('/auth/refresh_access_token', data);
	},
	autoLogin(data) {
		console.log(data);
		return api.post('/auth/auto_login', data);
	},
	test() {
		return api.post('/user/test');
	},
};
