import { ModuleBuilder, GetterHandler, BareActionContext, getStoreBuilder } from 'vuex-typex';
import { RootState } from '@/store';

export class StoreModuleBuilder<ModuleState> {
    private moduleBuilder: ModuleBuilder<ModuleState, RootState>;
    public constructor(name: string, initialState: ModuleState) {
        this.moduleBuilder = getStoreBuilder<RootState>().module(name, initialState);
    }

    public mutation(handler: (state: ModuleState, payload: any) => void): (payload?: any) => void {
        return this.moduleBuilder.commit(handler);
    }

    public state() {
        return this.moduleBuilder.state();
    }

    public action(
        handler: (context: BareActionContext<ModuleState, RootState>, payload: any) => void
    ): () => Promise<void> {
        return this.moduleBuilder.dispatch(handler);
    }

    public getter<G, T>(handler: GetterHandler<ModuleState, RootState, G, T>) {
        return this.moduleBuilder.read(handler);
    }
}
