import Vue from 'vue';
import Vuex from 'vuex';
import { getStoreBuilder } from 'vuex-typex';

import './modules/auth';
import './modules/account';
import './modules/alert';

import { AuthState } from './modules/auth';
import { AccountState } from './modules/account';
import { AlertState } from './modules/alert';

Vue.use(Vuex);

export interface RootState {
    account: AccountState;
    auth: AuthState;
    alert: AlertState;
}

const buildStore = () => getStoreBuilder<RootState>().vuexStore();
export default buildStore;
