import authApi from '@/api/auth';
import { RootState } from '@/store';
import { createModuleBuilder } from '@/store/helpers';

const LOCAL_STORAGE_KEY = 'auth';

interface AuthUserInfo {
    username: string;
    email: string;
}
interface AuthInfo {
    user: AuthUserInfo;
    token: string;
}
export interface AuthState {
    isLoading: boolean;
    isLoggedIn: boolean;
    error: string | null;
    auth: AuthInfo | null;
}

const authInfo = readAuthFromLocalStorage();
const initialState: AuthState = {
    isLoading: false,
    isLoggedIn: !!(authInfo && authInfo.token),
    error: null,
    auth: authInfo,
};
const { mutation, action, getter, moduleBuilder } = createModuleBuilder<AuthState, RootState>(
    'auth',
    initialState
);

const getters = {
    user: getter(function user(state: AuthState) {
        return state.auth && state.auth.user;
    }),
};

const mutations = {
    setAuth: mutation(function setAuth(state, auth: AuthInfo) {
        state.auth = auth;
    }),
    loginRequestBegin: mutation(function loginRequestBegin(state) {
        state.isLoading = true;
        state.error = null;
    }),
    loginSuccess: mutation(function loginSuccess(state) {
        state.isLoading = false;
        state.isLoggedIn = true;
    }),
    loginError: mutation(function loginError(state, payload: string) {
        state.isLoading = false;
        state.error = payload;
    }),
    logout: mutation(function logout(state) {
        state.isLoggedIn = false;
        state.auth = null;
    }),
    clearError: mutation(function clearError(state) {
        state.error = null;
    }),
};

export default {
    get state(): AuthState {
        return moduleBuilder.state()();
    },

    get currentUser() {
        return getters.user();
    },

    login: action(async function login(context, payload: { username: string; password: string }) {
        const { username, password } = payload;
        mutations.loginRequestBegin();
        try {
            const response = await authApi.authenticate({ username, password });
            const authInfo: AuthInfo = response.data;
            saveAuthToLocalStorage(authInfo);
            mutations.setAuth(authInfo);
            mutations.loginSuccess();
        } catch (err) {
            let message = 'Sorry, something went wrong.';
            if (err.response.status === 401) {
                message = 'Incorrect username or password.';
            }
            mutations.loginError(message);
        }
    }),

    logout: action(function logout() {
        mutations.logout();
        localStorage.removeItem(LOCAL_STORAGE_KEY);
    }),

    clearError: mutations.clearError,
};

function readAuthFromLocalStorage(): AuthInfo | null {
    let token = null;
    const authDataStr = localStorage.getItem(LOCAL_STORAGE_KEY);
    if (authDataStr) {
        try {
            token = JSON.parse(authDataStr);
        } catch (err) {
            console.log('Error parsing auth token');
        }
    }
    return token;
}

function saveAuthToLocalStorage(auth: AuthInfo) {
    localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(auth));
}
