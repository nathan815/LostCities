import { createModuleBuilder } from '@/store/helpers';
import { RootState } from '@/store';
import * as gameApi from '@/api/game';
import { Subscription } from 'rxjs';
import { GameData } from '@/store/modules/game/model';

export interface GameStoreState {
    gameData: any;
    error: string | null;
}

const initialState: GameStoreState = {
    gameData: {},
    error: null,
};

const { mutation, action, getter, getState } = createModuleBuilder<GameStoreState, RootState>(
    'game',
    initialState
);

const mutations = {
    setGameData: mutation(function setGameState(state, gameData: GameData) {
        console.log('Received game data: ', gameData);
        state.gameData = gameData;
    }),
};

let updateSubscription: Subscription;

export default {
    get state() {
        return getState();
    },

    loadInitialGameData: action(async function startGame(context, payload: { id: number }) {
        const gameData = await gameApi.requestInitialGameData(payload.id);
        mutations.setGameData(gameData);
    }),

    subscribeForUpdates: action(async function subscribeForUpdates(context, payload: { id: number }) {
        updateSubscription = gameApi.gameStateObservable(payload.id).subscribe(gameData => {
            mutations.setGameData(gameData);
        });
    }),

    unsubscribeFromUpdates: action(async function unsubscribeFromUpdates() {
        updateSubscription.unsubscribe();
    }),
};
