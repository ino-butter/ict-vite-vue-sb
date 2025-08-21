<template>
	<div>
		<div class="flex items-center max-w-200 my-5 mx-5 px-6 py-3 bg-white rounded-lg shadow-md">
			<div class="w-[800px] h-[460px]">
				<p class="text-right text-lg font-bold">{{ getToday }}</p>
				<div class="bg-red-300 flex gap-x-3">
					<div v-for="(day, index) in getDays" :key="index">
						<div
							class="w-14 h-16 bg-white rounded-lg shadow-md text-xs py-1 border border-gray-300"
						>
							<p class="text-xl font-bold text-center">21</p>
							<p class="text-lg text-center">오늘</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { useCounterStore } from '@/store/counter';
const store = useCounterStore();
const { getDays, getToday } = store;
</script>
<style></style>
