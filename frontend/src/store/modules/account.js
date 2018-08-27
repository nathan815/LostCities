import accountApi from '@/api/account';

const state = {
    error: null,
};

const actions = {
    async register({ commit }, data) {
        if(data.password !== data.confirmPassword) {
            commit('registerError', 'Passwords do not match');
            return;
        }
        commit('clearError');
        try {
            await accountApi.register(data);
        } catch(err) {
            commit('setError', 'Unable to create account');
        }
    },
    clearError({ commit }) {
        commit('clearError');
    },
};

const mutations = {
    setError(state, error) {
        state.error = error;
    },
    clearError(state) {
        state.error = null;
    },
};

export default {
    namespaced: true,
    state,
    actions,
    mutations,
};