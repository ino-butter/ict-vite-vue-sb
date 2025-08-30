\# 영화 예매 App



\## 📌 소개

공백



!\[Demo](https://user-images.githubusercontent.com/your-demo-gif.gif)



---



\## 목차

\- \[소개](#소개)

\- \[구현된 기능](#구현된-기능-2025-08-31-기준)

\- \[설치/셋팅](#설치셋팅)

&nbsp; - \[프론트 엔드 (Vite + Vue)](#프론트-엔드-vite--vue)

&nbsp; - \[백엔드 (SpringBoot3)](#백엔드-springboot3)

&nbsp; - \[DataBase (sqlite)](#database-sqlite)

&nbsp; - \[Redis (Docker)](#redis-docker)

---



\## 🎯 구현된 기능 2025-08-31 기준

\- 로그인/자동 로그인

\- 영화 목록 조회 

\- 영화 예매 조회

\- 좌석 선택 및 예매



---



\## 💻 설치/셋팅



\### 1. 프론트 엔드 (Vite + Vue)

\- IDE : VSCode

\- Port : 5173

\#### 필수 Extensions

\- Vetur

\- Volar

\#### 권장 Extensions

\- Auto Close Tag

\- ESLint

\- HTML CSS Support

\- Prettier - Code formatter

\#### 스타일 Extensions

\- Material Icon Theme

\- Night Owl

\#### NVM

\- Node.js : 22.18.0

\- NPM : 10.9.3



```bash

\# 터미널 명령어

npm install

```



\#### 프로젝트 로드

```bash

\# 로컬 서버 실행

npm run dev

```



```bash

\# settings.json

{

&nbsp; "workbench.colorTheme": "Night Owl",

&nbsp; "workbench.iconTheme": "material-icon-theme",



&nbsp; "eslint.validate": \[

&nbsp;   {

&nbsp;     "language": "vue",

&nbsp;     "autoFix": true

&nbsp;   },

&nbsp;   {

&nbsp;     "language": "javascript",

&nbsp;     "autoFix": true

&nbsp;   },

&nbsp;   {

&nbsp;     "language": "javascriptreact",

&nbsp;     "autoFix": true

&nbsp;   },

&nbsp;   {

&nbsp;     "language": "typescript",

&nbsp;     "autoFix": true

&nbsp;   },

&nbsp;   {

&nbsp;     "language": "typescriptreact",

&nbsp;     "autoFix": true

&nbsp;   }

&nbsp; ],

&nbsp; "editor.codeActionsOnSave": {

&nbsp;   "source.fixAll.eslint": "explicit"

&nbsp; },

&nbsp; // don't format on save

&nbsp; "editor.formatOnSave": false,



&nbsp; "tailwindCSS.includeLanguages": {

&nbsp;   "vue": "html",

&nbsp;   "vue-html": "html"

&nbsp; },

&nbsp; "editor.quickSuggestions": {

&nbsp;   "strings": "on"

&nbsp; },

&nbsp; "prettier.singleQuote": true,

&nbsp; "prettier.printWidth": 90

}



```



\### 2. 백엔드 엔드 (SpringBoot3)

\- IDE : sts-4.31.0.RELEASE

\- Port : 8085

\- JDK : 17.0.12 2024-07-16 LTS

\- Build Tool : Maven



\### 3. DataBase (sqlite)

\- 기본 경로 : C:/sql/databaseTest.sqlite

\- HeidiSQL



\### 4. Redis (Docker)

\- Host : locahost

\- Port : 6379

