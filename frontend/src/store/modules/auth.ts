import authApi from '@/api/auth';

const LOCAL_STORAGE_KEY = 'auth';

interface AuthUserInfo {
    username: string;
    email: string;
}
interface AuthInfo {
    user: AuthUserInfo;
    token: string;
}

function readAuthFromLocalStorage(): AuthInfo | null {
    let token = null;
    try {
        token = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY) || '');
    } catch (err) {
        console.log('Error parsing auth token');
    }
    return token;
}

function saveAuthToLocalStorage(auth: AuthInfo) {
    localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(auth));
}

const auth = readAuthFromLocalStorage();
const state = {
    isLoading: false,
    isLoggedIn: !!auth,
    error: null,
    user: auth ? auth.user : null,
    token: auth ? auth.token : null,
};

const actions = {
    async login({ commit }, { username, password }) {
        commit('loginRequest');
        try {
            const response = await authApi.authenticate({ username, password });
            if (response.data.token) {
                saveAuthToLocalStorage(response.data);
                commit('setToken', response.data.token);
                commit('setUser', response.data.user);
                commit('loginSuccess');
            } else {
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
    clearError({ commit }) {
        commit('clearError');
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
