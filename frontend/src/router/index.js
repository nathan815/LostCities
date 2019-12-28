import Vue from 'vue';
import Router from 'vue-router';
import routes from './routes';
import store from '../store';

Vue.use(Router);

const router = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    linkActiveClass: 'active',
    linkExactActiveClass: 'exact-active',
    routes: routes,
});

router.beforeEach((to, from, next) => {
    const loggedIn = store.state.auth.isLoggedIn;

    if (to.meta.requiresAuth && !loggedIn) {
        return next('/login');
    }

    if (to.meta.requiresGuest && loggedIn) {
        return next('/');
    }

    store.dispatch('alert/dismiss');

    next();
});

export default router;
