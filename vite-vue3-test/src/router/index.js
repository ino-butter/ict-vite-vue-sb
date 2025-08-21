import { createRouter, createWebHistory } from 'vue-router';

const routes = [
	{
		path: '/',
		redirect: '/login',
	},
	{
		path: '/login',
		component: () => import('@/views/LoginPage.vue'),
	},
	{
		path: '/signup',
		component: () => import('@/views/SignupPage.vue'),
	},
	{
		path: '/main',
		component: () => import('@/views/MainPage.vue'),
	},
	{
		path: '/bookticket',
		component: () => import('@/views/BookTicketPage.vue'),
	},
];

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes,
});

export default router;
