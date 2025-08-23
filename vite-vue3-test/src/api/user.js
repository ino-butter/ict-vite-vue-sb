import api from './index';

export const userAPI = {
	login(data) {
		return api.post('/auth/login', data);
	},
	refreshAccessToken(data) {
		return api.post('/auth/refresh_access_token', data);
	},
	test() {
		return api.post('/user/test');
	},
};
