import { createModuleBuilder } from '@/store/helpers';
import { RootState } from '@/store';

interface AlertOptions {
    message: string | null;
    variant: string | null;
}
interface AlertState extends AlertOptions {}

const initialState: AlertState = {
    message: null,
    variant: null,
};
const { mutation, getState } = createModuleBuilder<AlertState, RootState>(
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
    dismiss: mutations.dismiss,
};
