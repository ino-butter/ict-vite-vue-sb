<template>
	<div class="relative w-full h-full text-[1.4rem]">
		<AppHeader></AppHeader>
		<div class="flex flex-wrap items-center justify-center">
			<router-view></router-view>
		</div>
	</div>
</template>

<script setup>
import AppHeader from '@/components/common/AppHeader.vue';
</script>

<style></style>
