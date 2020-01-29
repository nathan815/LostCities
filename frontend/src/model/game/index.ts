import { Card, Discard } from '@/model/game/card';
import { Player } from '@/model/game/player';
import { Move } from '@/model/game/moves';

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
    public id: number = 0;
    public status: GameStatus = GameStatus.New;
    public deckSize: number = 0;

    public board: Board = {
        discard: {},
    };

    private currentTurnPlayerId: number = 0;
    public players: Player[] = [];
    public moves: Move[] = [];

    public hand: Card[] = [];

    constructor(state?: any) {
        Object.assign(this, state);
        this.hand = this.hand.map(Card.fromObject).sort(Card.compare);
        this.moves = this.moves.map(Move.fromObject);
    }

    get isNew() {
        return this.status === GameStatus.New;
    }

    get isReadyToStart() {
        return this.status === GameStatus.ReadyToStart;
    }

    get isStarted() {
        return this.status === GameStatus.Started;
    }

    get currentTurnPlayer(): Player | undefined {
        return this.findPlayerById(this.currentTurnPlayerId);
    }

    findPlayerById(id: number): Player | undefined {
        return this.players.find(player => player.id == id);
    }
}
