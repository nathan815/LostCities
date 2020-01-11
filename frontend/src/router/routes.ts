import Home from '@/views/Home.vue';
import Login from '@/views/auth/Login.vue';
import Register from '@/views/auth/Register.vue';
import GamePlay from '@/views/game/GamePlay.vue';
import Games from '@/views/game-list/Games.vue';

export default [
    {
        path: '/',
        component: Home,
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

    {
        path: '/games',
        component: Games,
    },

    {
        path: '/games/:id',
        component: GamePlay,
        meta: { requiresAuth: true },
    },
];
