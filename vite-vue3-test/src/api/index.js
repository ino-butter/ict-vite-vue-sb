import axios from 'axios';

const api = axios.create({
	baseURL: 'http://localhost:8085', // 여기만 수정하면 됨
	headers: {
		'Content-Type': 'application/json',
	},
});

api.interceptors.request.use(
	config => {
		return config;
	},
	error => {
		return Promise.reject(error);
	},
);

api.interceptors.response.use(
	response => {
		return response;
	},
	error => {
		return Promise.reject(error);
	},
);

export default api;
