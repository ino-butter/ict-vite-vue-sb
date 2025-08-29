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
		meta: { auth: true },
	},
	{
		path: '/bookticket',
		component: () => import('@/views/BookTicketPage.vue'),
				meta: { auth: true },
	},
];

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes,
});

// 네비게이션 가드
router.beforeEach((to, from, next) => {
	// 로그인 필요 페이지 && Refresh Token 없으면 로그인 페이지로 이동
	if (to.meta.auth) {
		const refreshToken = localStorage.getItem('REFRESH_TOKEN'); // Pinia store의 ref
		if (!refreshToken) {
			alert('다시 로그인 해주세요');
			next('/login');
			return;
		}
	}

	next(); // 조건에 안 걸리면 그냥 이동
});

export default router;
