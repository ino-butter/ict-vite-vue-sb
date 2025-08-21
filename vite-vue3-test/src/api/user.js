import api from './index';

export const userAPI = {
	getTest(data) {
		return api.post('/user/login', data);
	},
};
