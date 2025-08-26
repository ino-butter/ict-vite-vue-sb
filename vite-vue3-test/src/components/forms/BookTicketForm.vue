<template>
	<div class="max-w-6xl mx-auto my-5 bg-white rounded-lg shadow-md overflow-hidden">
		<!-- 전체 4컬럼 -->
		<div class="flex h-[600px]">
			<!-- 영화 -->
			<div class="flex-1 border-r border-gray-300 flex flex-col">
				<!-- 헤더 -->
				<div class="bg-green-500 text-white font-bold text-center py-2"> 영화 </div>
				<!-- 리스트 -->
				<div class="flex-1 overflow-y-auto bg-[#fdfbf5]">
					<ul class="px-3 py-2 text-left text-sm space-y-2">
						<li v-for="(movie, idx) in movieRelease" :key="idx" class="flex items-center gap-2">
							<span
								class="text-white text-xs px-1 rounded"
								:class="{
									'bg-green-600': movie.AGE_LIMIT === 'ALL',
									'bg-yellow-500': movie.AGE_LIMIT === '12',
									'bg-orange-600': movie.AGE_LIMIT === '15',
									'bg-red-600': movie.AGE_LIMIT === '18',
								}"
							>
								{{ movie.AGE_LIMIT }}
							</span>
							{{ movie.TITLE }}
						</li>
					</ul>
				</div>
			</div>

			<!-- 지역 -->
			<div class="flex-1 border-r border-gray-300 flex flex-col">
				<div class="bg-green-500 text-white font-bold text-center py-2"> 지역 </div>
				<div class="flex-1 overflow-y-auto bg-[#fdfbf5]">
					<ul class="px-3 py-2 text-left text-sm space-y-2">
						<li>서울({{ getCinemasByRegion('서울').count }})</li>
						<li>경기({{ getCinemasByRegion('경기').count }})</li>
						<li>인천({{ getCinemasByRegion('인천').count }})</li>
						<li>부산({{ getCinemasByRegion('부산').count }})</li>
						<li>울산({{ getCinemasByRegion('울산').count }})</li>
						<li>경주({{ getCinemasByRegion('경주').count }})</li>
						<li>제주({{ getCinemasByRegion('제주').count }})</li>
						<li>전라({{ getCinemasByRegion('전라').count }})</li>
					</ul>
				</div>
			</div>

			<!-- 극장 -->
			<div class="flex-1 border-r border-gray-300 flex flex-col">
				<div class="bg-green-500 text-white font-bold text-center py-2"> 극장 </div>
				<div class="flex-1 overflow-y-auto bg-[#fdfbf5]">
					<ul class="px-3 py-2 text-left text-sm space-y-2" v-if="selectRegion !== ''">
						<li v-for="(cinema, idx) in getCinemasByRegion(selectRegion).cinemas" :key="idx">
							{{ cinema.NAME }}
						</li>
					</ul>
				</div>
			</div>

			<!-- 날짜 -->
			<div class="w-40 border-r border-gray-300 flex flex-col">
				<div class="bg-green-500 text-white font-bold text-center py-2"> 날짜 </div>
				<div class="flex-1 overflow-y-auto bg-[#fdfbf5] text-center">
					<p class="font-bold py-2">2022.10</p>
					<ul class="space-y-1">
						<li class="text-blue-600">토 15</li>
						<li class="text-red-600">일 16</li>
						<li>월 17</li>
						<li>화 18</li>
						<li>수 19</li>
						<li>목 20</li>
						<li>금 21</li>
						<li class="text-blue-600">토 22</li>
						<li class="text-red-600">일 23</li>
					</ul>
				</div>
			</div>

			<!-- 시간 -->
			<div class="flex-1 flex flex-col">
				<div class="bg-green-500 text-white font-bold text-center py-2"> 시간 </div>
				<div
					class="flex-1 overflow-y-auto bg-[#fdfbf5] text-center flex items-center justify-center text-gray-500"
				>
					영화, 극장, 날짜를 선택해주세요.
				</div>
			</div>
		</div>
	</div>
</template>
<script setup>
import { useMovieStore } from '@/store/movie';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';

const movieStore = useMovieStore();
const { movieRelease } = storeToRefs(movieStore);
const getCinemasByRegion = movieStore.getCinemasByRegion;

const selectRegion = ref('');
</script>
<style></style>
