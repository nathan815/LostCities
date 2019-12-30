import Vue from 'vue';
import Vuex from 'vuex';
import { getStoreBuilder } from 'vuex-typex';

import './modules/auth';
import { AuthState } from './modules/auth';

Vue.use(Vuex);

export interface RootState {
    auth: AuthState;
}

const store = getStoreBuilder<RootState>().vuexStore();
export default store;
