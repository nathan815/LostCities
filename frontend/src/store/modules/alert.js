const state = {
    message: null,
    variant: null,
};

const actions = {
    show({ commit }, options) {
        commit('show', options);
    },
    dismiss({ commit, state }) {
        if (state.message) {
            commit('dismiss');
        }
    },
};

const mutations = {
    show(state, { message, variant }) {
        state.message = message;
        state.variant = variant;
    },
    dismiss(state) {
        state.message = null;
    },
};

export default {
    namespaced: true,
    state,
    actions,
    mutations,
};
