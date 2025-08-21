import globals from 'globals';
import pluginVue from 'eslint-plugin-vue';
import pluginPrettier from 'eslint-plugin-prettier'; // prettier 플러그인 import
import prettierConfig from 'eslint-config-prettier'; // prettier 충돌 방지 설정 import
import { fixupPlugin } from '@eslint/compat'; // 플러그인 호환성 도구 import

export default [
	{
		files: ['**/*.{js,jsx,mjs,cjs,ts,tsx,vue}'],
		languageOptions: {
			ecmaVersion: 'latest',
			sourceType: 'module',
			globals: {
				...globals.browser,
				...globals.node,
			},
		},
	},
	...pluginVue.configs['flat/essential'],
	{
		// Prettier 플러그인 활성화
		plugins: {
			prettier: fixupPlugin(pluginPrettier),
		},
		rules: {
			'vue/multi-word-component-names': 'off',
		},
	},
	prettierConfig, // ESLint 규칙과 Prettier 규칙 간의 충돌을 방지합니다.
];
