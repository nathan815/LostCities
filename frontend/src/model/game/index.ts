import { Card, Discard } from './card';
import { Player } from '@/model/game/player';

export interface Board {
    discard: Discard;
}

export enum GameStatus {
    New = 'New',
    ReadyToStart = 'ReadyToStart',
    Started = 'Started',
    Ended = 'Ended',
}

export class GameState {
    id: number = 0;
    status: GameStatus = GameStatus.New;
    deckSize: number = 0;
    board: Board = {
        discard: {},
    };
    players: Player[] = [];
    hand: Card[] = [];

    constructor(state?: any) {
        Object.assign(this, state);
    }

    get isNew() {
        return this.status === GameStatus.New;
    }

    get isReadyToStart() {
        return this.status === GameStatus.ReadyToStart;
    }
}
