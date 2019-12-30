import accountApi from '@/api/account';
import { createModuleBuilder } from '@/store/helpers';
import { RootState } from '@/store';

export interface AccountState {
    registerError: string | null;
}
const initialState: AccountState = {
    registerError: null,
};

const { mutation, action, getState } = createModuleBuilder<AccountState, RootState>(
    'account',
    initialState
);
const mutations = {
    setRegisterError: mutation(function setRegisterError(state, error: string) {
        state.registerError = error;
    }),
    clearRegisterError: mutation(function clearRegisterError(state) {
        state.registerError = null;
    }),
};

export interface RegisterData {
    email: string;
    username: string;
    password: string;
    confirmPassword: string;
}

export default {
    get state() {
        return getState();
    },
    register: action(async function register(context, data: RegisterData) {
        mutations.clearRegisterError();
        if (data.password !== data.confirmPassword) {
            mutations.setRegisterError('Passwords do not match');
            return;
        }
        try {
            await accountApi.register(data);
        } catch (err) {
            mutations.setRegisterError('Unable to create account');
        }
    }),
    clearRegisterError: mutations.clearRegisterError,
};
