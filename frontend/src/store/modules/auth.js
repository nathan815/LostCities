import authApi from '@/api/auth';
import router from '@/router';

const LOCAL_STORAGE_KEY = 'auth';

let auth = null;
try {
    auth = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY));
} catch(err) { }

const state = {
    isLoading: false,
    isLoggedIn: !!auth,
    user: auth ? auth.user : null,
    error: null,
    token: null,
};

const actions = {
    async login({ commit }, { username, password }) {
        commit('loginRequest');
        try {
            const response = await authApi.authenticate({ username, password });
            if (response.data.token) {
                localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(response.data));
                commit('setToken', response.data.token);
                commit('setUser', response.data.user);
                commit('loginSuccess');
                router.push('/');
            }
            else {
                throw new Error(response.data);
            }
        } catch (err) {
            let message = 'Sorry, something went wrong.';
            if (err.response.status === 401) {
                message = 'Incorrect username or password.';
            }
            commit('loginError', message);
        }
    },
    logout({ commit }) {
        localStorage.removeItem(LOCAL_STORAGE_KEY);
        commit('logout');
    },
};

const mutations = {
    setToken(state, token) {
        state.token = token;
    },
    setUser(state, user) {
        state.user = user;
    },
    loginRequest(state) {
        state.isLoading = true;
        state.error = null;
    },
    loginSuccess(state) {
        state.isLoading = false;
        state.isLoggedIn = true;
    },
    loginError(state, error) {
        state.isLoading = false;
        state.error = error;
    },
    logout(state) {
        state.isLoggedIn = false;
        state.token = null;
        state.user = null;
    },
};

export default {
    namespaced: true,
    state,
    actions,
    mutations,
};