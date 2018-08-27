import Vue from 'vue';
import BootstrapVue from 'bootstrap-vue'

import App from './App.vue';
import router from './router/router';
import store from './store/store';

Vue.config.productionTip = false;
Vue.use(BootstrapVue);

new Vue({
    el: '#app',
    render: h => h(App),
    router,
    store,
});