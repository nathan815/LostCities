import { Card, Discard } from '@/model/game/card';
import { Player } from '@/model/game/player';

export class Board {
    discard: Discard = {};
    constructor(discard: Discard) {
        const sortedDiscard = {};
        Object.keys(discard).forEach(
            color => (sortedDiscard[color] = discard[color].map(Card.fromObject))
        );
        this.discard = sortedDiscard;
    }
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
        this.hand = this.hand.map(Card.fromObject).sort(Card.compare);
    }

    get isNew() {
        return this.status === GameStatus.New;
    }

    get isReadyToStart() {
        return this.status === GameStatus.ReadyToStart;
    }
}
