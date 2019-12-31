import { createModuleBuilder } from '@/store/helpers';
import { RootState } from '@/store';

interface AlertOptions {
    message: string | null;
    variant: string | null;
}
export interface AlertState extends AlertOptions {}

const initialState: AlertState = {
    message: null,
    variant: null,
};
const { mutation, action, getState } = createModuleBuilder<AlertState, RootState>(
    'alert',
    initialState
);

const mutations = {
    show: mutation(function show(state, options: AlertOptions) {
        state.message = options.message;
        state.variant = options.variant;
    }),
    dismiss: mutation(function dismiss(state) {
        state.message = null;
    }),
};

export default {
    get state() {
        return getState();
    },
    show: mutations.show,
    dismiss: action(function dismiss({ state }) {
        if (state.message) {
            mutations.dismiss();
        }
    }),
};
