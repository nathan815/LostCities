import Vue from 'vue';
import Router from 'vue-router';
import routes from './routes';
import auth from '@/store/modules/auth';
import alert from '@/store/modules/alert';

Vue.use(Router);

const router = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    linkActiveClass: 'active',
    linkExactActiveClass: 'exact-active',
    routes: routes,
});

router.beforeEach((to, from, next) => {
    const { isLoggedIn } = auth.state;

    if (to.meta.requiresAuth && !isLoggedIn) {
        return next(`/login?to=${to.fullPath}`);
    }

    if (to.meta.requiresGuest && isLoggedIn) {
        return next('/');
    }

    alert.dismiss();
    next();
});

export default router;
