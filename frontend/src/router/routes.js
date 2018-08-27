import Home from '@/views/Home.vue'
import Login from '@/views/auth/Login.vue';
import Register from '@/views/auth/Register.vue';

export default [

    {
        path: '/',
        component: Home
    },

    {
        path: '/login',
        component: Login,
        meta: { requiresGuest: true },
    },

    {
        path: '/register',
        component: Register,
        meta: { requiresGuest: true },
    },

];