import { getStoreBuilder } from 'vuex-typex';

export function createModuleBuilder<S, R>(name: string, initialState: S) {
    const builder = getStoreBuilder<R>().module(name, initialState);
    const { commit, dispatch, read } = builder;
    return {
        moduleBuilder: builder,
        mutation: commit.bind(builder),
        action: dispatch.bind(builder),
        getter: read.bind(builder),
    };
}
