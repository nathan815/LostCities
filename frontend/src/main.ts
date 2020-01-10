import Vue from 'vue';
import BootstrapVue from 'bootstrap-vue';
import './registerServiceWorker';

import App from './views/App.vue';
import router from './router';
import buildStore from './store';

import '@/styles/main.scss';

Vue.config.productionTip = false;
Vue.use(BootstrapVue);

const store = buildStore();
new Vue({
    router,
    store,
    el: '#app',
    render: h => h(App),
});
