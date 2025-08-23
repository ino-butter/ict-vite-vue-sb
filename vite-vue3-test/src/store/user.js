import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import { userAPI } from '@/api/user';

export const useUserStore = defineStore('user', () => {
   
    async function login(email, pw) {
        try {
            const reponse = await userAPI.getTest({
                email: email,
                pw: pw,
            });
            console.log(reponse.data);
        } catch (error) {
            console.log(error);
        }
    }

    return { login };
});
