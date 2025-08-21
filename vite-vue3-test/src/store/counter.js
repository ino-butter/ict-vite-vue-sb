import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import { userAPI } from '@/api/user';

const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];

export const useCounterStore = defineStore('counter', () => {
	//const count = ref(0)
	//const doubleCount = computed(() => count.value * 2)
	//function increment() {
	//  count.value++
	//}

	const today = new Date('2025-08-20T00:00:00');
	const getDays = computed(() => {
		const dateList = [];
		const currentMonth = today.getMonth(); // 현재 달을 저장
		const currentDay = today.getDate(); // 현재 날짜를 저장

		// 시작 날짜(오늘)부터 7일치 날짜를 계산
		for (let i = 0; i < 7; i++) {
			const date = new Date(today);
			date.setDate(currentDay + i);

			// 계산된 날짜가 현재 달에 속하는지 확인
			// 만약 다음 달로 넘어가면 반복문을 종료
			if (date.getMonth() !== currentMonth) {
				break;
			}

			const month = String(date.getMonth() + 1).padStart(2, '0');
			const day = String(date.getDate()).padStart(2, '0');
			const dayOfWeek = daysOfWeek[date.getDay()];

			dateList.push({
				date: `${month}.${day}`,
				dayOfWeek: dayOfWeek,
				isToday: i === 0,
			});
		}
		return dateList;
	});
	const getToday = computed(() => {
		const currnetYear = today.getFullYear();
		const month = String(today.getMonth() + 1).padStart(2, '0');
		const day = String(today.getDate()).padStart(2, '0');
		const dayOfWeek = daysOfWeek[today.getDay()];
		return `${currnetYear}.${month}.${day} (${dayOfWeek})`;
	});

	async function test() {
		try {
			const reponse = await userAPI.getTest({
				username: 'test',
			});
			console.log(reponse.data);
		} catch (error) {
			console.log(error);
		}
	}

	return { getDays, getToday, test };
});
