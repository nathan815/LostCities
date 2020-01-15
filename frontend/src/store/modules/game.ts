import { createModuleBuilder } from '@/store/helpers';
import { RootState } from '@/store';
import * as gameApi from '@/api/game';
import { Subscription } from 'rxjs';

export enum Color {
    Yellow = 'yellow',
    Blue = 'blue',
    White = 'white',
    Green = 'green',
    Red = 'red',
}

export class Card {
    value: number = 0;
    color: Color = Color.White;
    instance?: number;

    constructor(value: number, color: Color, instance?: number) {
        this.value = value;
        this.color = color;
        this.instance = instance;
    }

    get isWager() {
        return this.value === 0;
    }

    public toString() {
        return this.color + ' ' + (this.isWager ? `wager ${this.instance}` : this.value);
    }
}

export type Discard = {
    [color: string]: Card[];
};

export type CardsInPlay = {
    [color: string]: Card[];
};

export interface GameData {
    id: number;
    discard: Discard;
    player1: any;
    player2: any;
}

export interface GameState {
    gameData: any;
    error: string | null;
}

const initialState: GameState = {
    gameData: {},
    error: null,
};

const { mutation, action, getter, getState } = createModuleBuilder<GameState, RootState>(
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
